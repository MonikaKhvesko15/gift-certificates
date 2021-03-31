package com.epam.esm.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T> implements Repository<T> {

    private final JdbcTemplate jdbcTemplate;

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

    protected Integer insertData(String query, Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                statement.setObject(i, params[i - 1]);
            }
            return statement;
        }, keyHolder);

        return (Integer) keyHolder.getKey();
    }

    @Override
    public List queryForListResult(Specification specification) {
        return null;
    }

    @Override
    public Optional queryForSingleResult(Specification specification) {
        return Optional.empty();
    }

    @Override
    public T update(T t) {
        return null;
    }
}
