package com.epam.esm.specification.tag;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Tag;
import com.epam.esm.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class TagAllSpecification implements CriteriaSpecification<Tag> {

    private static final Class<Tag> TAG_CLASS = Tag.class;

    @Override
    public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Tag> criteria = builder.createQuery(TAG_CLASS);
        Root<Tag> tagRoot = criteria.from(TAG_CLASS);
        criteria.select(tagRoot).distinct(true)
                .orderBy(builder.asc(tagRoot.get(BaseEntity.ID_ATTRIBUTE)));
        return criteria;
    }
}
