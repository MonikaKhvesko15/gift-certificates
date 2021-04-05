package com.epam.esm.dto.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

public class TagConverterDto {

    public static Tag convertToEntity(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }

    public static TagDto convertToDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

}
