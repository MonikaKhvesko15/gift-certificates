package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends AbstractRepository<User>{
    protected UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
