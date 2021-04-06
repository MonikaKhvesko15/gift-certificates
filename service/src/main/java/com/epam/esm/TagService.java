package com.epam.esm;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {
    TagDTO create(TagDTO tag);

    TagDTO getById(Long id);

    List<TagDTO> getAll();

    boolean remove(Long id);
}
