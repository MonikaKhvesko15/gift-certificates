package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.TagAllSpecification;
import com.epam.esm.specification.TagIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final Repository<Tag> tagRepository;

    @Autowired
    public TagServiceImpl(Repository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = new Tag(tagDto.getId(), tagDto.getName());
        tag = tagRepository.save(tag);
        return new TagDto(tag);
    }

    @Override
    public TagDto getById(String id) {
        Optional<Tag> optionalTag = tagRepository.queryForSingleResult(new TagIdSpecification(id));
        Tag tag = optionalTag.orElseThrow(EntityNotFoundException::new);
        return new TagDto(tag);
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
