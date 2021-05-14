package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.PageDTOLinkBuilder;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final LinkBuilder<OrderDTO> orderDTOLinkBuilder;
    private final PageDTOLinkBuilder<OrderDTO> pageDTOLinkBuilder;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO findById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.getById(id);
        orderDTOLinkBuilder.toModel(orderDTO);
        return orderDTO;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<OrderDTO> findAll(@Valid PageRequestDTO pageRequestDTO) {
        PageDTO<OrderDTO> pageDTO = orderService.findAll(pageRequestDTO);
        pageDTO.getContent().forEach(orderDTOLinkBuilder::toModel);
        pageDTOLinkBuilder.toModel(pageDTO);
        return pageDTO;
    }
}
