package com.utn.repository;

import com.utn.entities.Pedido;
import com.utn.entities.Usuario;
import com.utn.entities.Producto;
import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import com.utn.dtos.PedidoListadoDTO;
import com.utn.dtos.ItemPedidoDTO;
import com.utn.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PedidoRepository extends BaseRepository<Pedido> {

    public PedidoRepository() {
        super(Pedido.class);
    }

    public List<Pedido> buscarPorUsuario(Long idUsuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            // Consulta JPQL que retorna todos los pedidos activos de un usuario dado su ID.
            // Navega desde Usuario hacia su coleccion de pedidos y excluye bajas logicas.
            String jpql = "SELECT p FROM Usuario u " +
                    "JOIN u.pedidos p " +
                    "WHERE u.id = :idUsuario " +
                    "AND p.eliminado = false";

            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
            query.setParameter("idUsuario", idUsuario);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<Pedido> buscarPorEstado(Estado estado) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            // Consulta JPQL que retorna todos los pedidos activos con un estado especifico.
            // Permite filtrar pedidos PENDIENTE, CONFIRMADO, TERMINADO o CANCELADO.
            String jpql = "SELECT p FROM Pedido p " +
                    "WHERE p.estado = :estado " +
                    "AND p.eliminado = false";

            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
            query.setParameter("estado", estado);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<PedidoListadoDTO> listarActivosConUsuario() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            // Consulta JPQL que obtiene pedidos activos junto con el usuario al que pertenecen.
            // Se navega desde Usuario hacia su coleccion de pedidos porque Pedido no tiene referencia directa a Usuario.
            String jpql = "SELECT u, p FROM Usuario u " +
                    "JOIN u.pedidos p " +
                    "WHERE u.eliminado = false " +
                    "AND p.eliminado = false";

            List<Object[]> resultados = em.createQuery(jpql, Object[].class)
                    .getResultList();

            return resultados.stream()
                    .map(fila -> {
                        Usuario usuario = (Usuario) fila[0];
                        Pedido pedido = (Pedido) fila[1];

                        return new PedidoListadoDTO(
                                pedido.getId(),
                                pedido.getFecha(),
                                pedido.getEstado(),
                                pedido.getFormaPago(),
                                usuario.getNombre() + " " + usuario.getApellido(),
                                pedido.getTotal()
                        );
                    })
                    .toList();

        } finally {
            em.close();
        }
    }

    public Pedido registrarPedido(Long idUsuario, FormaPago formaPago, List<ItemPedidoDTO> items) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, idUsuario);

            if (usuario == null || usuario.isEliminado()) {
                throw new IllegalArgumentException("No existe un usuario activo con ese ID.");
            }

            if (items == null || items.isEmpty()) {
                throw new IllegalArgumentException("El pedido debe tener al menos un producto.");
            }

            Pedido pedido = Pedido.builder()
                    .fecha(LocalDate.now())
                    .estado(Estado.PENDIENTE)
                    .formaPago(formaPago)
                    .total(0.0)
                    .build();

            for (ItemPedidoDTO item : items) {
                Producto producto = em.find(Producto.class, item.productoId());

                if (producto == null || producto.isEliminado()) {
                    throw new IllegalArgumentException("No existe un producto activo con ID: " + item.productoId());
                }

                if (!Boolean.TRUE.equals(producto.getDisponible())) {
                    throw new IllegalArgumentException("El producto no esta disponible: " + producto.getNombre());
                }

                if (item.cantidad() <= 0) {
                    throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
                }

                if (producto.getStock() < item.cantidad()) {
                    throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
                }

                pedido.addDetallePedido(item.cantidad(), producto);

                producto.setStock(producto.getStock() - item.cantidad());
            }

            usuario.addPedido(pedido);

            em.persist(pedido);
            em.merge(usuario);

            em.getTransaction().commit();

            return pedido;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }
}