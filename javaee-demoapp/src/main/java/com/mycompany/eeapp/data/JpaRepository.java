package com.mycompany.eeapp.data;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class JpaRepository<T, K> {

    private final Class<T> entityClass;

    private final EntityManager entityManager;

    public JpaRepository(final Class<T> entityClass, final EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public T create(T entity) {
        entityManager.persist(entity);
	    entityManager.flush();
        return entity;
    }

    public T update(T entity) {
        T retEnt = entityManager.merge(entity);
	    entityManager.flush();
        return retEnt;
    }

    public void remove(T entity) {
        entityManager.remove(entity);
	    entityManager.flush();
    }

    public T findOne(K id) {
        return entityManager.find(entityClass, id);
    }

    public T findOneByNamedQuery(String queryName) {
        return entityManager
                .createNamedQuery(queryName, entityClass)
                .getSingleResult();
    }

    public <P> T findOneByNamedQuery(String queryName, String paramName, P paramValue) {
        return entityManager
                .createNamedQuery(queryName, entityClass)
                .setParameter(paramName, paramValue)
                .getSingleResult();
    }

    public List<T> findAll() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return entityManager.createQuery(cq).getResultList();
    }
}
