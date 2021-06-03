package com.epam.esm.service;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.entityDTO.TagDTO;

public interface TagService {
    TagDTO create(TagDTO tag);

    TagDTO getById(Long id);

    PageDTO<TagDTO> findAll(PageRequestDTO pageRequestDTO);

    void remove(Long id);

    TagDTO getMostPopularTag(Long userId);
}
