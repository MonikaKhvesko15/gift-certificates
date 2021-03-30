package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> findAll();

    void insert(Tag tag);

    void deleteById(Long id);
}
