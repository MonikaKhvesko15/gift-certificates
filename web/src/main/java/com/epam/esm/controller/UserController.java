package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.OrderDTOLinkBuilder;
import com.epam.esm.link.UserDTOLinkBuilder;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v2/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final LinkBuilder<UserDTO> userDTOLinkBuilder;
    private final LinkBuilder<OrderDTO> orderDTOLinkBuilder;

    public UserController(UserServiceImpl userService,
                          OrderService orderService,
                          UserDTOLinkBuilder userDTOLinkBuilder,
                          OrderDTOLinkBuilder orderDTOLinkBuilder) {
        this.userService = userService;
        this.orderService = orderService;
        this.userDTOLinkBuilder = userDTOLinkBuilder;
        this.orderDTOLinkBuilder = orderDTOLinkBuilder;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable Long id) {
        UserDTO userDTO = userService.getById(id);
        userDTOLinkBuilder.toModel(userDTO);
        return userDTO;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<UserDTO> findAll(@Valid PageRequestDTO pageRequestDTO) {
        PageDTO<UserDTO> pageDTO = userService.findAll(pageRequestDTO);
        pageDTO.getContent().forEach(userDTOLinkBuilder::toModel);
        return pageDTO;
    }

    @PostMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.create(id, orderDTO);
        orderDTOLinkBuilder.toModel(createdOrder);
        return createdOrder;
    }
}
