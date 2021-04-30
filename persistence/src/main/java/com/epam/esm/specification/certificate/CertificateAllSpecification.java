package com.epam.esm.specification.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.CriteriaSpecification;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

@NoArgsConstructor
public class CertificateAllSpecification implements CriteriaSpecification<Certificate> {

    @Override
    public CriteriaQuery<Certificate> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Certificate> criteria = builder.createQuery(Certificate.class);
        Root<Certificate> certificateRoot = criteria.from(Certificate.class);
        certificateRoot.fetch("tags", JoinType.LEFT);
        criteria.select(certificateRoot).distinct(true);
        return criteria;
    }
}