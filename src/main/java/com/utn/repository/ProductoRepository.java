package com.utn.repository;

import com.utn.entities.Producto;
import com.utn.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            // Consulta JPQL que obtiene productos activos asociados a una categoría específica.
            String jpql = "SELECT p FROM Producto p " +
                    "WHERE p.categoria.id = :categoriaId " +
                    "AND p.eliminado = false";

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("categoriaId", categoriaId);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<Producto> listarActivosConCategoria() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            String jpql = "SELECT p FROM Producto p " +
                    "JOIN FETCH p.categoria " +
                    "WHERE p.eliminado = false";

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);

            return query.getResultList();

        } finally {
            em.close();
        }
    }
}

