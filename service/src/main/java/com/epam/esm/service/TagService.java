package com.epam.esm.service;

import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {
    TagDTO create(TagDTO tag);

    TagDTO getById(Long id);

    List<TagDTO> findAll(PageRequestDTO pageRequestDTO);

    void remove(Long id);
}
