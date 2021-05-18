package com.epam.esm.service.impl;

import com.epam.esm.converter.DTOConverter;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.tag.TagAllSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends AbstractService<TagDTO, Tag> implements TagService {
    private final Repository<User> userRepository;
    private final TagRepository tagRepository;

    public TagServiceImpl(DTOConverter<Tag, TagDTO> converter,
                          TagRepository tagRepository,
                          Repository<User> userRepository) {
        super(converter, tagRepository);
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }


    @Override
    public TagDTO getById(Long id) {
        Tag tag = repository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(tag);
    }

    @Override
    public void remove(Long id) {
        Tag tag = converter.convertToEntity(getById(id));
        repository.delete(tag);
    }

    @Override
    public TagDTO getMostPopularTag(Long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + userId + ")"));
        Tag tag = tagRepository.getMostPopularTag(user.getId())
                .orElseThrow(EntityNotFoundException::new);
        return converter.convertToDto(tag);
    }

    @Override
    public PageDTO<TagDTO> findAll(PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<Tag> specification = new TagAllSpecification();
        List<Tag> tagList = repository.getEntityListBySpecification(specification,
                Integer.parseInt(pageRequestDTO.getPage().toString()),
                Integer.parseInt(pageRequestDTO.getSize().toString()));
        List<TagDTO> tagDTOList = converter.convertToListDTO(tagList);
        long totalElements = repository.countEntities(specification);
        return new PageDTO<>(
                Integer.parseInt(pageRequestDTO.getPage().toString()),
                Integer.parseInt(pageRequestDTO.getSize().toString()),
                totalElements,
                tagDTOList
        );
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        String tagName = tagDTO.getName();
        if (repository.getByName(tagName).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + tagName + ")");
        }
        Tag tag = converter.convertToEntity(tagDTO);
        return converter.convertToDto(repository.save(tag));
    }
}
