package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.specification.SqlSpecification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends Entity> implements Repository<T> {
    protected final JdbcTemplate jdbcTemplate;
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM %s WHERE id = ?";

    protected AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    @Override
    public boolean deleteById(Long id) {
        String query = String.format(DELETE_BY_ID_QUERY, getTableName());
        return jdbcTemplate.update(query, id) == 1;
    }

    protected Long insertData(String query, Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                statement.setObject(i, params[i - 1]);
            }
            return statement;
        }, keyHolder);

        return (Long) keyHolder.getKey();
    }

    @Override
    public List<T> queryForListResult(SqlSpecification specification) {
        return jdbcTemplate.query(specification.getSqlQuery(), getRowMapper());
    }

    @Override
    public Optional<T> queryForSingleResult(SqlSpecification specification) {
        List<T> items = queryForListResult(specification);
        return items.size() == 1 ? Optional.of(items.get(0)) : Optional.empty();
    }



}
