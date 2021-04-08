package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.specification.SqlSpecification;

import java.util.List;
import java.util.Set;

public interface CertificateRepository extends Repository<Certificate> {
    Certificate update(Certificate certificate);

    Set<Tag> createNewTags(Set<Tag> tags);

    Set<Tag> findNewTags(Set<Tag> tags);

    Set<Tag> createCertificateTags(Certificate certificate);

    void deleteCertificateTags(Long certificateId);

    List<Certificate> query(SqlSpecification specification);
}
