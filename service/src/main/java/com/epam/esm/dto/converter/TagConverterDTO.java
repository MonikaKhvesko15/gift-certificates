package com.epam.esm.dto.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagConverterDTO {
    private final ModelMapper modelMapper;

    @Autowired
    public TagConverterDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Tag convertToEntity(TagDTO tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    public TagDTO convertToDto(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }

}
