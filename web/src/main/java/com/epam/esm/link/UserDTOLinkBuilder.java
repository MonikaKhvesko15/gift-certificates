package com.epam.esm.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.entityDTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class UserDTOLinkBuilder implements LinkBuilder<UserDTO> {
    private static final Class<UserController> userControllerClass = UserController.class;
    private final OrderDTOLinkBuilder orderDTOLinkBuilder;

    @Override
    public void toModel(UserDTO userDTO) {
        if (userDTO.getOrders() != null) {
            userDTO.getOrders().forEach(orderDTOLinkBuilder::toModel);
        }
        userDTO.add(linkTo(userControllerClass)
                .withRel("users"));
        userDTO.add(linkTo(methodOn(userControllerClass)
                .findById(userDTO.getId()))
                .withSelfRel());
//        userDTO.add(linkTo(methodOn(UserController.class)
//                .update(userDTO, userDTO.getId()))
//                .withRel("update"));
//        userDTO.add(linkTo(userControllerClass)
//                .slash(userDTO.getId())
//                .withRel("delete"));
    }
}
