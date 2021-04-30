package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.metamodel.Certificate_;
import com.epam.esm.specification.CriteriaSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<Certificate> {
    private static final String GET_BY_ID_QUERY = "FROM Certificate c WHERE c.isDeleted = false AND id = :id";
    private static final String GET_BY_NAME_QUERY = "FROM Certificate c WHERE c.isDeleted = false AND c.name = :name";
    private static final String GET_ALL_QUERY = "SELECT c from Certificate c WHERE c.isDeleted = false";
    private static final Class<Certificate> certificateClass = Certificate.class;


    public CertificateRepositoryImpl(EntityManager entityManager) {
        super(entityManager, certificateClass);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Certificate certificate = entityManager.find(certificateClass, id);
        certificate.setDeleted(true);
        entityManager.merge(certificate);
    }

    @Override
    public List<Certificate> findAll() {
        return entityManager.createQuery(GET_ALL_QUERY, certificateClass)
                .getResultList();
    }

    @Override
    public Optional<Certificate> getByName(String name) {
        return entityManager.createQuery(GET_BY_NAME_QUERY)
                .setParameter("name", name)
                .getResultList().stream().findAny();
    }

    @Override
    public Optional<Certificate> getById(Long id) {
        return entityManager.createQuery(GET_BY_ID_QUERY)
                .setParameter("id", id)
                .getResultList().stream().findAny();
    }

    public List<Certificate> getEntityListBySpecification(CriteriaSpecification<Certificate> specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = specification.getCriteriaQuery(builder);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
