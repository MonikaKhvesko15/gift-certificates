package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.TagConverterDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.TagAllSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final Repository<Tag> tagRepository;

    @Autowired
    public TagServiceImpl(Repository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDTO create(TagDTO tagDto) {
        Tag tag = TagConverterDTO.convertToEntity(tagDto);
        tag = tagRepository.save(tag);
        return TagConverterDTO.convertToDto(tag);
    }

    @Override
    public TagDTO getById(Long id) {
        Tag tag = tagRepository.getById(id).orElseThrow(EntityNotFoundException::new);
        return TagConverterDTO.convertToDto(tag);
    }

    @Override
    public List<TagDTO> getAll() {
        List<Tag> tags = tagRepository.query(new TagAllSpecification());
        return tags.stream().map(TagConverterDTO::convertToDto).collect(Collectors.toList());
    }

    @Override
    public boolean remove(Long id) {
        return tagRepository.deleteById(id);
    }


}
