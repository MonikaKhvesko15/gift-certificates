package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.Specification;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class TagRepository extends AbstractRepository<Tag> {


    protected TagRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void add(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public boolean deleteById(Integer id) {

    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    public List queryListResult(Specification specification) {
        return null;
    }

    @Override
    public Optional querySingleResult(Specification specification) {
        return Optional.empty();
    }
}
