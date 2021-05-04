package com.epam.esm.specification.order;

import com.epam.esm.entity.Order;
import com.epam.esm.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class OrderAllSpecification implements CriteriaSpecification<Order> {
    @Override
    public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);
        criteria.select(orderRoot).distinct(true);
        return criteria;
    }
}
