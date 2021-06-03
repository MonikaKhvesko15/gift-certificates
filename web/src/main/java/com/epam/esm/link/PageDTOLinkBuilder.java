package com.epam.esm.link;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.entityDTO.CertificateDTO;
import com.epam.esm.dto.entityDTO.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.entityDTO.TagDTO;
import com.epam.esm.dto.entityDTO.UserDTO;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PageDTOLinkBuilder<T> implements LinkBuilder<PageDTO<T>> {

    private static final int INDEX_FIRST = 0;

    @Override
    public void toModel(PageDTO<T> pageDTO) {

        T entity = pageDTO.getContent().get(INDEX_FIRST);

        if (entity.getClass().equals(TagDTO.class)) {
            pageDTO.add(linkTo(TagController.class).withRel("tags"));
        }
        if (entity.getClass().equals(CertificateDTO.class)) {
            pageDTO.add(linkTo(CertificateController.class).withRel("certificates"));
        }
        if (entity.getClass().equals(OrderDTO.class)) {
            pageDTO.add(linkTo(OrderController.class).withRel("orders"));
        }
        if (entity.getClass().equals(UserDTO.class)) {
            pageDTO.add(linkTo(UserController.class).withRel("users"));
        }
    }
}
