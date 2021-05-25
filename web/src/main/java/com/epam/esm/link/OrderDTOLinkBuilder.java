package com.epam.esm.link;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class OrderDTOLinkBuilder implements LinkBuilder<OrderDTO> {
    private static final Class<OrderController> orderControllerClass = OrderController.class;
    private final CertificateDTOLinkBuilder certificateDTOLinkBuilder;

    @Override
    public void toModel(OrderDTO orderDTO) {
        if (orderDTO.getCertificates() != null) {
            orderDTO.getCertificates().forEach(certificateDTOLinkBuilder::toModel);
        }
        orderDTO.add(linkTo(orderControllerClass)
                .withRel("orders"));
        orderDTO.add(linkTo(methodOn(orderControllerClass)
                .findById(orderDTO.getId()))
                .withSelfRel());
//        orderDTO.add(linkTo(methodOn(OrderController.class)
//                .update(orderDTO, orderDTO.getId()))
//                .withRel("update"));
//        orderDTO.add(linkTo(orderControllerClass)
//                .slash(orderDTO.getId())
//                .withRel("delete"));
    }
}
