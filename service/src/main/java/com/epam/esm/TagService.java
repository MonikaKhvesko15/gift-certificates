package com.epam.esm;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto create(TagDto tag);

    TagDto getById(String id);

    List<TagDto> getAll();

    boolean remove(Long id);
}
