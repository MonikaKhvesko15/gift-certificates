package com.epam.esm.repository;

import com.epam.esm.entity.Certificate.Columns;
import com.epam.esm.entity.Entity;
import com.epam.esm.specification.SqlSpecification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends Entity> implements Repository<T> {
    private static final int NUMBER_UPDATED_ROWS = 1;
    protected final NamedParameterJdbcTemplate template;
    private static final String GET_BY_ID_QUERY = "SELECT * FROM %s WHERE id = :id";
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM %s WHERE name = :name";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM %s WHERE id = :id";

    protected AbstractRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    @Override
    public boolean deleteById(Long id) {
        String query = String.format(DELETE_BY_ID_QUERY, getTableName());
        SqlParameterSource param = new MapSqlParameterSource().addValue(Columns.ID.getColumn(), id);
        return template.update(query, param) == NUMBER_UPDATED_ROWS;
    }

    @Override
    public List<T> query(SqlSpecification specification) {
        return template.query(specification.getSqlQuery(), getRowMapper());
    }

    @Override
    public Optional<T> getById(Long id) {
        String query = String.format(GET_BY_ID_QUERY, getTableName());
        SqlParameterSource param = new MapSqlParameterSource().addValue(Columns.ID.getColumn(), id);
        return template.query(query, param, getRowMapper()).stream().findAny();
    }

    @Override
    public Optional<T> getByName(String name) {
        String query = String.format(GET_BY_NAME_QUERY, getTableName());
        SqlParameterSource param = new MapSqlParameterSource().addValue(Columns.NAME.getColumn(), name);
        return template.query(query, param, getRowMapper()).stream().findAny();
    }
}
