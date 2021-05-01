package com.epam.esm.link;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagDTOLinkBuilder implements LinkBuilder<TagDTO> {
    @Override
    public void buildEntityLink(TagDTO tagDTO) {
        tagDTO.add(linkTo(TagController.class).withRel("tags"));
        tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel());
    }
}
