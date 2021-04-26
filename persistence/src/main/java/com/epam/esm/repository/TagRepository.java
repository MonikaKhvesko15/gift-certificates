package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Set;

public interface TagRepository extends Repository<Tag> {
    void createCertificateTags(Long certificateId, Set<Tag> tags);
}
