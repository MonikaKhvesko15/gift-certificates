package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> {
    protected OrderRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Order.class);
    }
}
