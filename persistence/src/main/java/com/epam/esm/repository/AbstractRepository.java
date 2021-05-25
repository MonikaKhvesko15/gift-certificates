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
    protected static final String NAME_ATTRIBUTE = "name";
    @PersistenceContext
    protected final EntityManager entityManager;
    protected final CriteriaBuilder builder;
    protected final Class<T> entityClass;
    protected final CriteriaQuery<T> criteria;
    protected final Root<T> entityRoot;

    protected AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.entityClass = entityClass;
        this.criteria = builder.createQuery(entityClass);
        this.entityRoot = criteria.from(entityClass);
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
        Predicate namePredicate = builder.equal(entityRoot.get(NAME_ATTRIBUTE), name);
        return findSingleEntity(namePredicate);
    }

    @Override
    public Optional<T> getById(Long id) {
        Predicate idPredicate = builder.equal(entityRoot.get(BaseEntity.ID_ATTRIBUTE), id);
        return findSingleEntity(idPredicate);
    }

    private Optional<T> findSingleEntity(Predicate predicate) {
        Predicate isNotDeletedPredicate = builder.isFalse(entityRoot.get(BaseEntity.IS_DELETED_ATTRIBUTE));
        criteria.select(entityRoot).where(isNotDeletedPredicate, predicate);
        return findOrEmpty(criteria);
    }

    @Override
    @Transactional(readOnly = true)
    public long countEntities(CriteriaSpecification<T> specification) {
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        return entityManager.createQuery(criteriaQuery).getResultStream().count();
    }

    @Override
    public List<T> getEntityListBySpecification(CriteriaSpecification<T> specification,
                                                int pageNumber, int pageSize) {
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }


    @Override
    public Optional<T> getEntityBySpecification(CriteriaSpecification<T> specification) {
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        return findOrEmpty(criteriaQuery);
    }

    public Optional<T> findOrEmpty(CriteriaQuery<T> criteriaQuery) {
        try {
            EntityRetriever<T> retriever = () -> entityManager
                            .createQuery(criteriaQuery)
                            .getSingleResult();
            return Optional.of(retriever.retrieve());
        } catch (NoResultException ex) {
            log.warn("No matches found matching search criteria");
        }
        return Optional.empty();
    }
}
