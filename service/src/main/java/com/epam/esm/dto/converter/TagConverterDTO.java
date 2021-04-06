package com.epam.esm.dto.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

public class TagConverterDTO {

    public static Tag convertToEntity(TagDTO tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }

    public static TagDTO convertToDto(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName());
    }

}
