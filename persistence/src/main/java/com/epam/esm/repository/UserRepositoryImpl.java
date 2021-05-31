package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    protected UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        criteria.select(entityRoot)
                .where(builder.equal(entityRoot.get(User.EMAIL_ATTRIBUTE), email));
        return findOrEmpty(criteria);
    }
}
