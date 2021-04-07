package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Set;

public interface TagRepository extends Repository<Tag>{
    Tag getByName(String name);

    Set<Tag> getTagsByCertificateId(Long certificateId);
}
