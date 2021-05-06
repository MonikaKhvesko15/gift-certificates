package com.epam.esm.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagDTOConverter {
    private final ModelMapper modelMapper;

    public TagDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Tag convertToEntity(TagDTO tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    public TagDTO convertToDto(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }

    public List<TagDTO> convertToListDTO(List<Tag> tags) {
        List<TagDTO> tagDTOList = new ArrayList<>();
        tags.forEach(tag -> {
            TagDTO tagDTO = convertToDto(tag);
            tagDTOList.add(tagDTO);
        });
        return tagDTOList;
    }
}
