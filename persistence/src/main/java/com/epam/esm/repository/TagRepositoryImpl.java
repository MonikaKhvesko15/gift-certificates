package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> {
    private static final String GET_BY_NAME_QUERY = "FROM Tag t WHERE t.name = :name";
    private static final String GET_ALL_QUERY = "SELECT t from Tag t";

    private static final Class<Tag> tagClass = Tag.class;

    public TagRepositoryImpl(EntityManager entityManager) {
        super(entityManager, tagClass);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tag model = entityManager.find(tagClass, id);
        entityManager.remove(model);
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(GET_ALL_QUERY, tagClass)
                .getResultList();
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return entityManager.createQuery(GET_BY_NAME_QUERY)
                .setParameter("name", name)
                .getResultList().stream().findAny();
    }

    @Override
    public Optional<Tag> getById(Long id) {
        return Optional.ofNullable(entityManager.find(tagClass, id));
    }
}

