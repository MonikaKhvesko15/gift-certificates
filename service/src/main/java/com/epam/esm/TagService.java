package com.epam.esm;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    Long create(Tag tag);

    Tag getById(String id);

    List<Tag> getAll();

    boolean remove(Long id);
}
