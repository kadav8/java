package com.mycompany.jpa;

import javax.persistence.EntityManager;

public class JpaRepository<T, K> {
    private Class<T> entityClass;

    private EntityManager em;

    public JpaRepository(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.em = em;
    }

    public T findOne(K id) {
        return em.find(entityClass, id);
    }
}
