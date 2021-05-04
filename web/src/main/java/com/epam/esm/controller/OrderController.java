package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.OrderDTOLinkBuilder;
import com.epam.esm.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v2/orders")
public class OrderController {
    private final OrderService orderService;
    private final LinkBuilder<OrderDTO> linkBuilder;

    public OrderController(OrderService orderService,
                           OrderDTOLinkBuilder linkBuilder) {
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO findById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.getById(id);
        linkBuilder.toModel(orderDTO);
        return orderDTO;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<OrderDTO> orderDTOPage = orderService.findAll(pageable);
        orderDTOPage.getContent().forEach(linkBuilder::toModel);
        return orderDTOPage;
    }

    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@PathVariable Long userId, @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.create(userId, orderDTO);
        linkBuilder.toModel(createdOrder);
        return createdOrder;
    }
}
