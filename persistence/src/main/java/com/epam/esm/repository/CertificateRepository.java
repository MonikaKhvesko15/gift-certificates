package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.SqlSpecification;

import java.util.List;

public interface CertificateRepository extends Repository<Certificate> {
    Certificate update(Certificate certificate);

    void deleteCertificateTags(Long certificateId);

    List<Certificate> query(SqlSpecification specification);
}
