package com.epam.esm.service.impl;

import com.epam.esm.converter.TagDTOConverter;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.tag.TagAllSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepositoryImpl tagRepository;
    private final Repository<User> userRepository;
    private final TagDTOConverter converter;

    @Override
    public TagDTO getById(Long id) {
        Tag tag = tagRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        return converter.convertToDto(tag);
    }

    @Override
    public void remove(Long id) {
        Tag tag = converter.convertToEntity(getById(id));
        tagRepository.delete(tag);
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
        List<Tag> tagList = tagRepository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<TagDTO> tagDTOList = converter.convertToListDTO(tagList);
        int totalElements = tagRepository.countEntities(specification);
        return new PageDTO<>(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize(),
                totalElements,
                tagDTOList
        );
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
