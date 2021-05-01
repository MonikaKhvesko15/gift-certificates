package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.util.EntityRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends BaseEntity> implements Repository<T> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractRepository.class);

    @PersistenceContext
    protected final EntityManager entityManager;
    protected final CriteriaBuilder builder;
    private final Class<T> entityClass;

    protected AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
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

    @Override
    @Transactional
    public void deleteById(Long id) {
        T entity = entityManager.find(entityClass, id);
        entity.setDeleted(true);
        entityManager.merge(entity);
    }

    @Override
    public Optional<T> getByName(String name) {
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> entityRoot = criteria.from(entityClass);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isFalse(entityRoot.get("isDeleted")));
        predicates.add(builder.equal(entityRoot.get("name"), name));
        criteria.select(entityRoot)
                .where(predicates.toArray(new Predicate[]{}));

        return findOrEmpty(() ->
                entityManager
                        .createQuery(criteria)
                        .getSingleResult());
    }

    @Override
    public Optional<T> getById(Long id) {
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> entityRoot = criteria.from(entityClass);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isFalse(entityRoot.get("isDeleted")));
        predicates.add(builder.equal(entityRoot.get("id"), id));
        criteria.select(entityRoot)
                .where(predicates.toArray(new Predicate[]{}));

        return findOrEmpty(() ->
                entityManager
                        .createQuery(criteria)
                        .getSingleResult());
    }

    @Override
    public List<T> getEntityListBySpecification(CriteriaSpecification<T> specification) {
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        return entityManager.createQuery(criteriaQuery).getResultList();
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
            LOGGER.warn("Founded value is empty");
        }
        return Optional.empty();
    }
}
