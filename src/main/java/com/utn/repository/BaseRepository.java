package com.utn.repository;

import com.utn.entities.Base;
import com.utn.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends Base> {

    private final EntityManagerFactory emf;
    private final Class<T> clazz;

    public BaseRepository(Class<T> clazz) {
        this.clazz = clazz;
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    public T guardar(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            T entidadGuardada = em.merge(entity);

            em.getTransaction().commit();

            return entidadGuardada;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public Optional<T> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            T entidad = em.find(clazz, id);

            return Optional.ofNullable(entidad);

        } finally {
            em.close();
        }
    }

    public List<T> listarActivos() {
        EntityManager em = emf.createEntityManager();

        try {
            String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.eliminado = false";

            return em.createQuery(jpql, clazz)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public boolean eliminarLogico(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            T entidad = em.find(clazz, id);

            if (entidad == null || entidad.isEliminado()) {
                em.getTransaction().rollback();
                return false;
            }

            entidad.setEliminado(true);

            em.merge(entidad);

            em.getTransaction().commit();

            return true;

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