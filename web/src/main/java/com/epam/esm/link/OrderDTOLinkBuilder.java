package com.epam.esm.link;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDTOLinkBuilder implements LinkBuilder<OrderDTO> {
    @Override
    public void toModel(OrderDTO orderDTO) {
        orderDTO.add(linkTo(OrderController.class).withRel("orders"));
        orderDTO.add(linkTo(methodOn(OrderController.class).findById(orderDTO.getId())).withSelfRel());
    }
}
