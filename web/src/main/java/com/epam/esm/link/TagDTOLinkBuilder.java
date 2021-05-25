package com.epam.esm.link;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagDTOLinkBuilder implements LinkBuilder<TagDTO> {
    private static final Class<TagController> tagControllerClass = TagController.class;

    @Override
    public void toModel(TagDTO tagDTO) {
        tagDTO.add(linkTo(tagControllerClass)
                .withRel("tags"));
        tagDTO.add(linkTo(methodOn(tagControllerClass)
                .findById(tagDTO.getId()))
                .withSelfRel());
        tagDTO.add(linkTo(tagControllerClass)
                .slash(tagDTO.getId())
                .withRel("delete"));
    }
}
