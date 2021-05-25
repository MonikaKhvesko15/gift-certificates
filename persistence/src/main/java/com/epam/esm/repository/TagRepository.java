package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagRepository extends Repository<Tag>{
    Optional<Tag> getMostPopularTag(Long userId);
}
