package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractRepository<T extends BaseEntity> implements Repository<T> {

    @PersistenceContext
    protected final EntityManager entityManager;

    private final Class<T> entityClass;

    protected AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public T save(T model) {
        entityManager.persist(model);
        return model;
    }

    @Override
    @Transactional
    public T update(T model) {
        return entityManager.merge(model);
    }

}
