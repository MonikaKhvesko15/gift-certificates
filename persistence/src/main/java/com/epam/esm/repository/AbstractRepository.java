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

    protected AbstractRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    protected abstract RowMapper<T> getRowMapper();

    protected String getByIdQuery;

    protected String getByNameQuery;

    protected String deleteByIdQuery;

    @Override
    public boolean deleteById(Long id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue(Columns.ID.getColumn(), id);
        return template.update(deleteByIdQuery, param) == NUMBER_UPDATED_ROWS;
    }

    @Override
    public Optional<T> getById(Long id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue(Columns.ID.getColumn(), id);
        return template.query(getByIdQuery, param, getRowMapper()).stream().findAny();
    }

    @Override
    public Optional<T> getByName(String name) {
        SqlParameterSource param = new MapSqlParameterSource().addValue(Columns.NAME.getColumn(), name);
        return template.query(getByNameQuery, param, getRowMapper()).stream().findAny();
    }

    @Override
    public List<T> query(SqlSpecification specification) {
        return template.query(specification.getSqlQuery(), getRowMapper());
    }
}
