package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

public interface TagRepository extends Repository<Tag>{
    Tag getByName(String name);
}
