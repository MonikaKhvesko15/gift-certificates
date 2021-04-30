package com.epam.esm.specification.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.specification.CriteriaSpecification;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CertificateByParamsSpecification implements CriteriaSpecification<Certificate> {
    private final String tagName;
    private final String name;
    private final String description;
    private final String sortBy;
    private final String order;

    @Override
    public CriteriaQuery<Certificate> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Certificate> criteria = builder.createQuery(Certificate.class);
        Root<Certificate> certificateRoot = criteria.from(Certificate.class);
        List<Predicate> predicates = new ArrayList<>();

        addTagNamePredicate(builder, criteria, certificateRoot, predicates);
        addPartNamePredicate(builder, certificateRoot, predicates);
        addPartDescriptionPredicate(builder, certificateRoot, predicates);

        Predicate[] arrayPredicates = new Predicate[predicates.size()];
        predicates.toArray(arrayPredicates);
        criteria.where(builder.and(arrayPredicates));
        if (sortBy == null) {
            return criteria.orderBy(builder.asc(certificateRoot.get("id")));
        }
        //todo: sortBy and order
        return criteria;
    }

    private void addTagNamePredicate(
            CriteriaBuilder builder,
            CriteriaQuery<Certificate> criteria,
            Root<Certificate> certificateRoot,
            List<Predicate> predicates) {
        if (tagName != null) {
            Subquery<Certificate> subQuery = criteria.subquery(Certificate.class);
            Root<Certificate> subQueryCertificateRoot = subQuery.from(Certificate.class);
            Join<Certificate, Tag> certificateTagJoin = subQueryCertificateRoot.join("tags");
            Path<String> tagNamePath = certificateTagJoin.get("name");
            subQuery.select(subQueryCertificateRoot).distinct(true);
            subQuery.where(builder.equal(tagNamePath, tagName));
            Predicate predicate = builder.in(certificateRoot).value(subQuery);
            predicates.add(predicate);
        }
    }

    private void addPartNamePredicate(
            CriteriaBuilder builder,
            Root<Certificate> certificateRoot,
            List<Predicate> predicates) {
        if (name != null) {
            predicates.add(builder.like(
                    builder.upper(certificateRoot.get("name")), "%" + name.toUpperCase() + "%"));
        }
    }

    private void addPartDescriptionPredicate(
            CriteriaBuilder builder,
            Root<Certificate> certificateRoot,
            List<Predicate> predicates) {
        if (description != null) {
            predicates.add(
                    builder.like(
                            builder.upper(certificateRoot.get("description")), "%" + description.toUpperCase() + "%"));
        }
    }


}
