package br.com.fuctura.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import br.com.fuctura.util.JPAUtils;

import java.util.List;
import java.util.Optional;

public abstract class GenericDAO<T, ID> {
    protected EntityManager entityManager;
    private Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
    }

    public T save(T entity) {
        try {
            entityManager.getTransaction().begin();
            entity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            this.entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
        }
    }

    public Optional<T> findById(ID id) {
        try {
            T entity = entityManager.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            this.entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
        }
    }

    public List<T> findAll() {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);
            return entityManager.createQuery(cq).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            this.entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
        }
    }


    public T update(T entity) {
        try {
            entityManager.getTransaction().begin();
            entity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            this.entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
        }
    }

    public void deleteById(ID id) {
        try {
            entityManager.getTransaction().begin();
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            this.entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
        }
    }

    public void delete(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            this.entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
        }
    }

    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    public void closeFactory() {
        JPAUtils.closeEntityManagerFactory();
    }
}