package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.Set;

public interface CertificateRepository extends Repository<Certificate> {
    Certificate update(Certificate certificate);

    Set<Tag> createNewTags(Set<Tag> tags);

    Set<Tag> findNewTags(Set<Tag> tags);

    Set<Tag> createCertificateRefsToTags(Certificate certificate);

    void deleteOldCertificateRefsToTags(Long certificateId);
}
