package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.TagAllSpecification;
import com.epam.esm.specification.TagIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final Repository<Tag> tagRepository;

    @Autowired
    public TagServiceImpl(Repository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Long create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getById(String id) {
        return tagRepository.queryForSingleResult(new TagIdSpecification(id)).get();
    }

    @Override
    public List getAll() {
        return tagRepository.queryForListResult(new TagAllSpecification());
    }

    @Override
    public boolean remove(Long id) {
        return tagRepository.deleteById(id);
    }


}
