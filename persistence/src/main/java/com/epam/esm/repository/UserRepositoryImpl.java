package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User>{
    protected UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
