package com.epam.esm.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class UserDTOLinkBuilder implements LinkBuilder<UserDTO> {
    private final OrderDTOLinkBuilder orderDTOLinkBuilder;

    @Override
    public void toModel(UserDTO userDTO) {
        if (userDTO.getOrders() != null) {
            userDTO.getOrders().forEach(orderDTOLinkBuilder::toModel);
        }
        userDTO.add(linkTo(UserController.class).withRel("users"));
        userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
    }
}
