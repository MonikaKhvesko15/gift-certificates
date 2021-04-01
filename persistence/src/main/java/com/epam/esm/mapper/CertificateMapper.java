package com.epam.esm.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String description = rs.getString("description");
        Double price = rs.getDouble("price");
        Integer duration = rs.getInt("duration");

        String name = rs.getString("name");
        LocalDate createDate = rs.getDate("create_date").toLocalDate();
        LocalDate lastUpdateDate = rs.getDate("last_update_date").toLocalDate();


        return new Certificate.Builder(name, createDate, lastUpdateDate)
                .id(id)
                .description(description)
                .price(price)
                .duration(duration)
                .build();
    }
}
