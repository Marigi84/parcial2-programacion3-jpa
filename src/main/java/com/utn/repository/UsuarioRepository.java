package com.utn.repository;

import com.utn.entities.Usuario;
import com.utn.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Optional<Usuario> buscarPorMail(String mail) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            // Consulta JPQL que busca un usuario activo por su dirección de correo electrónico.
            // Se filtra por eliminado = false para excluir usuarios dados de baja lógica.
            String jpql = "SELECT u FROM Usuario u " +
                    "WHERE u.mail = :mail " +
                    "AND u.eliminado = false";

            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("mail", mail);

            List<Usuario> usuarios = query.getResultList();

            return usuarios.stream().findFirst();

        } finally {
            em.close();
        }
    }
}