package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.TagAllSpecification;
import com.epam.esm.specification.TagSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagServiceImpl implements TagService {
    private final Repository tagRepository;

    @Autowired
    public TagServiceImpl(Repository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.queryForListResult(new TagAllSpecification());
    }

    @Override
    public Tag getTag(){
        return (Tag) tagRepository.queryForSingleResult(new TagSpecification()).get();
    }


}
