package com.epam.esm.mapper;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate.Columns;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong(Columns.ID.getColumn());
        String description = rs.getString(Columns.DESCRIPTION.getColumn());
        BigDecimal price = rs.getBigDecimal(Columns.PRICE.getColumn());
        int duration = rs.getInt(Columns.DURATION.getColumn());

        String name = rs.getString(Columns.NAME.getColumn());
        LocalDateTime createDate = rs.getTimestamp(Columns.CREATE_DATE.getColumn()).toLocalDateTime();
        LocalDateTime lastUpdateDate = rs.getTimestamp(Columns.LAST_UPDATE_DATE.getColumn()).toLocalDateTime();
        boolean isDeleted = rs.getBoolean(Columns.IS_DELETED.getColumn());


        return new Certificate.Builder(name, description, price, duration)
                .id(id)
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .isDeleted(isDeleted)
                .build();
    }
}
