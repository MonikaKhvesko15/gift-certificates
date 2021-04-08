package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.Set;

public interface TagRepository extends Repository<Tag> {
    Set<Tag> getTagsByCertificateId(Long certificateId);

    Set<Tag> createNewTags(Set<Tag> tags);

    Set<Tag> createCertificateTags(Certificate certificate);
}
