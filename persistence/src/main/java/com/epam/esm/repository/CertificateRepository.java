package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CertificateRepository extends AbstractRepository<Certificate> {

    public CertificateRepository(EntityManager entityManager) {
        super(entityManager, Certificate.class);
    }
}
