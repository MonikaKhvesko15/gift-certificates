package com.epam.esm.dto.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;


public class TagConverterDTO {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Tag convertToEntity(TagDTO tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        return tag;
    }

    public static TagDTO convertToDto(Tag tag) {
        TagDTO tagDTO = modelMapper.map(tag, TagDTO.class);
        return tagDTO;
    }

}
