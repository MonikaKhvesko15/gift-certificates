package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.specification.SqlSpecification;

import java.util.List;
import java.util.Set;

public interface CertificateRepository extends Repository<Certificate> {
    Certificate update(Certificate certificate);

    void deleteCertificateTags(Long certificateId);

    List<Certificate> query(SqlSpecification specification);
}
