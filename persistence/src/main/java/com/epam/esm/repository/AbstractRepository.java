package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.util.EntityRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractRepository<T extends BaseEntity> implements Repository<T> {
    @PersistenceContext
    protected final EntityManager entityManager;
    protected final CriteriaBuilder builder;
    protected final Class<T> entityClass;

    protected AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        entity.setDeleted(true);
        entityManager.merge(entity);
    }

    @Override
    public Optional<T> getByName(String name) {
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> entityRoot = criteria.from(entityClass);
        Predicate isNotDeletedPredicate = builder.isFalse(entityRoot.get("isDeleted"));
        Predicate namePredicate = builder.equal(entityRoot.get("name"), name);
        criteria.select(entityRoot).where(isNotDeletedPredicate, namePredicate);
        return findOrEmpty(() ->
                entityManager
                        .createQuery(criteria)
                        .getSingleResult());
    }

    @Override
    public Optional<T> getById(Long id) {
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> entityRoot = criteria.from(entityClass);
        Predicate isNotDeletedPredicate = builder.isFalse(entityRoot.get("isDeleted"));
        Predicate idPredicate = builder.equal(entityRoot.get("id"), id);
        criteria.select(entityRoot).where(isNotDeletedPredicate, idPredicate);
        return findOrEmpty(() ->
                entityManager
                        .createQuery(criteria)
                        .getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public int countEntities(CriteriaSpecification<T> specification) {
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().size();
    }

    @Override
    public List<T> getEntityListBySpecification(CriteriaSpecification<T> specification,
                                                Integer pageNumber, Integer pageSize) {
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Optional<T> getEntityBySpecification(CriteriaSpecification<T> specification) {
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        return findOrEmpty(() ->
                entityManager
                        .createQuery(criteriaQuery)
                        .getSingleResult());
    }

    public static <T> Optional<T> findOrEmpty(final EntityRetriever<T> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException ex) {
            log.warn("No matches found matching search criteria");
        }
        return Optional.empty();
    }
}
