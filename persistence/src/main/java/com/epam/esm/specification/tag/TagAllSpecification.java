package com.epam.esm.specification.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class TagAllSpecification implements CriteriaSpecification<Tag> {
    @Override
    public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteria.from(Tag.class);
        criteria.select(tagRoot).distinct(true);
        return criteria;
    }
}
