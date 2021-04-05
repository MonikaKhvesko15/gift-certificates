package com.epam.esm.mapper;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate.Columns;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong(Columns.ID.getColumn());
        String description = rs.getString(Columns.DESCRIPTION.getColumn());
        Double price = rs.getDouble(Columns.PRICE.getColumn());
        Integer duration = rs.getInt(Columns.DURATION.getColumn());

        String name = rs.getString(Columns.NAME.getColumn());
        LocalDate createDate = rs.getDate(Columns.CREATE_DATE.getColumn()).toLocalDate();
        LocalDate lastUpdateDate = rs.getDate(Columns.LAST_UPDATE_DATE.getColumn()).toLocalDate();


        return new Certificate.Builder(name, createDate, lastUpdateDate)
                .id(id)
                .description(description)
                .price(price)
                .duration(duration)
                .build();
    }
}
