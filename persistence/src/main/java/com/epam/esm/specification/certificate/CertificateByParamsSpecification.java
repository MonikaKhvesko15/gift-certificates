package com.epam.esm.specification.certificate;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.specification.CriteriaSpecification;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String DATE_ATTRIBUTE = "date";
    private static final Class<Certificate> CERTIFICATE_CLASS = Certificate.class;
    private final List<String> tags;
    private final String name;
    private final String description;
    private final String sortBy;
    private final String order;

    @Override
    public CriteriaQuery<Certificate> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Certificate> criteria = builder.createQuery(CERTIFICATE_CLASS);
        Root<Certificate> certificateRoot = criteria.from(CERTIFICATE_CLASS);
        certificateRoot.fetch(Certificate.TAGS_ATTRIBUTE, JoinType.LEFT);
        criteria.select(certificateRoot).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        Predicate isNotDeletedPredicate = builder.isFalse(certificateRoot.get(BaseEntity.IS_DELETED_ATTRIBUTE));
        predicates.add(isNotDeletedPredicate);
        if (CollectionUtils.isNotEmpty(tags)) {
            List<Predicate> listTagsPredicate = getListTagsPredicate(builder, criteria, certificateRoot);
            predicates.addAll(listTagsPredicate);
        }
        if (StringUtils.isNotEmpty(name)) {
            Predicate partNamePredicate = getPartNamePredicate(builder, certificateRoot);
            predicates.add(partNamePredicate);
        }
        if (StringUtils.isNotEmpty(description)) {
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
        Subquery<Certificate> subQuery = criteria.subquery(CERTIFICATE_CLASS);
        Root<Certificate> subQueryCertificateRoot = subQuery.from(CERTIFICATE_CLASS);
        Join<Certificate, Tag> certificateTagJoin = subQueryCertificateRoot.join(Certificate.TAGS_ATTRIBUTE);
        Path<String> tagNamePath = certificateTagJoin.get(Tag.NAME_ATTRIBUTE);
        subQuery.select(subQueryCertificateRoot).distinct(true);
        subQuery.where(builder.equal(tagNamePath, tagName));
        return subQuery;
    }

    private Predicate getPartNamePredicate(CriteriaBuilder builder,
                                           Root<Certificate> certificateRoot) {
        return builder.like(
                builder.upper(certificateRoot.get(Certificate.NAME_ATTRIBUTE)), "%" + name.toUpperCase() + "%");
    }

    private Predicate getPartDescriptionPredicate(CriteriaBuilder builder,
                                                  Root<Certificate> certificateRoot) {
        return builder.like(
                builder.upper(certificateRoot.get(Certificate.DESCRIPTION_ATTRIBUTE)), "%" + description.toUpperCase() + "%");
    }

    public void sort(CriteriaQuery<Certificate> criteria,
                     CriteriaBuilder builder,
                     Root<Certificate> root) {

        if (Certificate.NAME_ATTRIBUTE.equalsIgnoreCase(sortBy)) {
            if (ASC.equalsIgnoreCase(order)) {
                criteria.orderBy(builder.asc(root.get(Certificate.NAME_ATTRIBUTE)));
            } else if (DESC.equalsIgnoreCase(order)) {
                criteria.orderBy(builder.desc(root.get(Certificate.NAME_ATTRIBUTE)));
            }
        }
        if (DATE_ATTRIBUTE.equalsIgnoreCase(sortBy)) {
            if (ASC.equalsIgnoreCase(order)) {
                criteria.orderBy(builder.asc(root.get(Certificate.CREATE_DATE_ATTRIBUTE)));
            } else if (DESC.equalsIgnoreCase(order)) {
                criteria.orderBy(builder.desc(root.get(Certificate.CREATE_DATE_ATTRIBUTE)));
            }
        }
    }
}
