package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    TagDTO create(TagDTO tag);

    TagDTO getById(Long id);

    Page<TagDTO> findAll(Pageable pageable);

    void remove(Long id);
}
