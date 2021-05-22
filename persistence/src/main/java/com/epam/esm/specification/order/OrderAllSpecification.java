package com.epam.esm.specification.order;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Order;
import com.epam.esm.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class OrderAllSpecification implements CriteriaSpecification<Order> {

    private static final Class<Order> ORDER_CLASS = Order.class;

    @Override
    public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Order> criteria = builder.createQuery(ORDER_CLASS);
        Root<Order> orderRoot = criteria.from(ORDER_CLASS);
        criteria.select(orderRoot).distinct(true)
                .orderBy(builder.asc(orderRoot.get(BaseEntity.ID_ATTRIBUTE)));
        return criteria;
    }
}
