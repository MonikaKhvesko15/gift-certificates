package com.epam.esm.specification.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.specification.CriteriaSpecification;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CertificateByParamsSpecification implements CriteriaSpecification<Certificate> {
    private final List<String> tags;
    private final String name;
    private final String description;
    private final String sortBy;
    private final String order;

    @Override
    public CriteriaQuery<Certificate> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Certificate> criteria = builder.createQuery(Certificate.class);
        Root<Certificate> certificateRoot = criteria.from(Certificate.class);
        certificateRoot.fetch("tags", JoinType.LEFT);
        criteria.select(certificateRoot).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        Predicate isNotDeletedPredicate = builder.isFalse(certificateRoot.get("isDeleted"));
        predicates.add(isNotDeletedPredicate);
        if (tags != null) {
            List<Predicate> listTagsPredicate = getListTagsPredicate(builder, criteria, certificateRoot);
            predicates.addAll(listTagsPredicate);
        }
        if (name != null) {
            Predicate partNamePredicate = getPartNamePredicate(builder, certificateRoot);
            predicates.add(partNamePredicate);
        }
        if (description != null) {
            Predicate partDescriptionPredicate = getPartDescriptionPredicate(builder, certificateRoot);
            predicates.add(partDescriptionPredicate);
        }
        Predicate[] arrayPredicates = new Predicate[predicates.size()];
        predicates.toArray(arrayPredicates);
        criteria.where(arrayPredicates);

        sort(criteria, builder, certificateRoot);
        return criteria;
    }

    private List<Predicate> getListTagsPredicate(CriteriaBuilder builder,
                                                 CriteriaQuery<Certificate> criteria,
                                                 Root<Certificate> certificateRoot) {
        List<Predicate> predicates = new ArrayList<>();
        for (String tagName : tags) {
            Subquery<Certificate> subQuery = getTagNameSubQuery(builder, criteria, tagName);
            Predicate predicate = builder.in(certificateRoot).value(subQuery);
            predicates.add(predicate);
        }
        return predicates;
    }

    private Subquery<Certificate> getTagNameSubQuery(CriteriaBuilder builder,
                                                     CriteriaQuery<Certificate> criteria,
                                                     String tagName) {
        Subquery<Certificate> subQuery = criteria.subquery(Certificate.class);
        Root<Certificate> subQueryCertificateRoot = subQuery.from(Certificate.class);
        Join<Certificate, Tag> certificateTagJoin = subQueryCertificateRoot.join("tags");
        Path<String> tagNamePath = certificateTagJoin.get("name");
        subQuery.select(subQueryCertificateRoot).distinct(true);
        subQuery.where(builder.equal(tagNamePath, tagName));
        return subQuery;
    }

    private Predicate getPartNamePredicate(CriteriaBuilder builder,
                                           Root<Certificate> certificateRoot) {
        return builder.like(
                builder.upper(certificateRoot.get("name")), "%" + name.toUpperCase() + "%");
    }

    private Predicate getPartDescriptionPredicate(CriteriaBuilder builder,
                                                  Root<Certificate> certificateRoot) {
        return builder.like(
                        builder.upper(certificateRoot.get("description")), "%" + description.toUpperCase() + "%");
    }

    public void sort(CriteriaQuery<Certificate> criteria,
                     CriteriaBuilder builder,
                     Root<Certificate> root) {

        if (sortBy.equalsIgnoreCase("name")) {
            if (order.equalsIgnoreCase("ASC")) {
                criteria.orderBy(builder.asc(root.get("name")));
            } else if (order.equalsIgnoreCase("DESC")) {
                criteria.orderBy(builder.desc(root.get("name")));
            }
        }
        if (sortBy.equalsIgnoreCase("date")) {
            if (order.equalsIgnoreCase("ASC")) {
                criteria.orderBy(builder.asc(root.get("create_date")));
            } else if (order.equalsIgnoreCase("DESC")) {
                criteria.orderBy(builder.desc(root.get("create_date")));
            }
        }
    }
}
