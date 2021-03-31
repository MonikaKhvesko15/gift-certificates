package com.epam.esm.repository;

import com.epam.esm.specification.Specification;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T> implements Repository {

    private JdbcTemplate jdbcTemplate;

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM %s WHERE id = ?;";

    protected AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean deleteById(Integer id) {
        String query = String.format(DELETE_BY_ID_QUERY, getTableName());
        return jdbcTemplate.update(query, 1) == 1;
    }

    protected abstract String getTableName();

    @Override
    public List queryListResult(Specification specification) {
        return null;
    }

    @Override
    public Optional querySingleResult(Specification specification) {
        return Optional.empty();
    }
}
