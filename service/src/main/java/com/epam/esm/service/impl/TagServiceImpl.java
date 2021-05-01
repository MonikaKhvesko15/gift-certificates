package com.epam.esm.service.impl;

import com.epam.esm.converter.TagDTOConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.tag.TagAllSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepositoryImpl tagRepository;
    private final TagDTOConverter converter;

    @Autowired
    public TagServiceImpl(TagRepositoryImpl tagRepository,
                          TagDTOConverter converter) {
        this.tagRepository = tagRepository;
        this.converter = converter;
    }

    @Override
    public TagDTO getById(Long id) {
        Tag tag = tagRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(tag);
    }

    @Override
    public void remove(Long id) {
        if (!tagRepository.getById(id).isPresent()) {
            throw new EntityNotFoundException(" (id = " + id + ")");
        }
        tagRepository.deleteById(id);
    }

    @Override
    public List<TagDTO> findAll() {
        CriteriaSpecification<Tag> specification = new TagAllSpecification();
        List<Tag> tags = tagRepository.getEntityListBySpecification(specification);
        return converter.convertToListDTO(tags);
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        String tagName = tagDTO.getName();
        if (tagRepository.getByName(tagName).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + tagName + ")");
        }
        Tag tag = converter.convertToEntity(tagDTO);
        return converter.convertToDto(tagRepository.save(tag));
    }
}
