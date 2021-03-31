package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class CertificateRepository extends AbstractRepository<Certificate> {


    private static final String GIFT_CERTIFICATES_TABLE_NAME = "gift_certificates";

    protected CertificateRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Certificate add(Certificate certificate) {
        return null;
    }

    @Override
    public Certificate update(Certificate certificate) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {

    }

    @Override
    protected String getTableName() {
        return GIFT_CERTIFICATES_TABLE_NAME;
    }

    @Override
    public List queryForListResult(Specification specification) {
        return null;
    }

    @Override
    public Optional queryForSingleResult(Specification specification) {
        return Optional.empty();
    }
}
