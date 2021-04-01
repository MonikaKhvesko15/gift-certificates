package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.TagAllSpecification;

import java.util.List;

public class TagServiceImpl implements TagService {
    private final Repository<Tag> tagRepository;

    public TagServiceImpl(Repository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.queryForListResult(new TagAllSpecification());
    }
}
