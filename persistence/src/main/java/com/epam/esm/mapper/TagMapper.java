package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag.Columns;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong(Columns.ID.getColumn());
        String name = rs.getString(Columns.NAME.getColumn());
        return new Tag(id, name);
    }
}
