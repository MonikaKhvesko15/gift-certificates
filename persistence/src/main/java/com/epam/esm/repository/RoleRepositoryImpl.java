package com.epam.esm.repository;

import com.epam.esm.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> {
    protected RoleRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Role.class);
    }

    @Override
    public Optional<Role> getByName(String name) {
        Predicate namePredicate = builder.equal(entityRoot.get(NAME_ATTRIBUTE), name);
        criteria.select(entityRoot).where(namePredicate);
        return findOrEmpty(criteria);
    }
}
