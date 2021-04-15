package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.TagConverterDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.TagAllSpecification;
import com.epam.esm.validator.TagDTOValidator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final Validator<TagDTO> validator;
    private final TagConverterDTO converter;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagDTOValidator validator, TagConverterDTO converter) {
        this.tagRepository = tagRepository;
        this.validator = validator;
        this.converter = converter;
    }

    @Override
    public TagDTO getById(Long id) {
        Tag tag = tagRepository.getById(id).orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(tag);
    }

    @Override
    public List<TagDTO> getAll() {
        List<Tag> tags = tagRepository.query(new TagAllSpecification());
        return tags.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TagDTO create(TagDTO tagDto) {
        validator.cleanErrorMessages();
        if (!validator.isValid(tagDto)) {
            throw new ValidatorException(validator.getErrorMessage());
        }
        String tagName = tagDto.getName();
        if (tagRepository.getByName(tagName).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + tagName + ")");
        }
        Tag tag = converter.convertToEntity(tagDto);
        tag = tagRepository.save(tag);
        return converter.convertToDto(tag);
    }


    @Override
    public boolean remove(Long id) {
        return tagRepository.deleteById(id);
    }
}
