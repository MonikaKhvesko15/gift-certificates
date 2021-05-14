package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.PageDTOLinkBuilder;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
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
@RequestMapping(value = "/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;
    private final LinkBuilder<UserDTO> userDTOLinkBuilder;
    private final LinkBuilder<OrderDTO> orderDTOLinkBuilder;
    private final LinkBuilder<TagDTO> tagDTOLinkBuilder;
    private final PageDTOLinkBuilder<UserDTO> pageUserDTOLinkBuilder;
    private final PageDTOLinkBuilder<OrderDTO> pageOrderDTOLinkBuilder;

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
        pageUserDTOLinkBuilder.toModel(pageDTO);
        return pageDTO;
    }

    @PostMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.create(id, orderDTO);
        orderDTOLinkBuilder.toModel(createdOrder);
        return createdOrder;
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<OrderDTO> getAllUserOrders(@PathVariable Long id,
                                              @Valid PageRequestDTO pageRequestDTO) {
        PageDTO<OrderDTO> orderDTOPage = orderService.getAllUserOrders(id, pageRequestDTO);
        orderDTOPage.getContent().forEach(orderDTOLinkBuilder::toModel);
        pageOrderDTOLinkBuilder.toModel(orderDTOPage);
        return orderDTOPage;
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getUserOrder(@PathVariable Long id,
                                 @PathVariable Long orderId,
                                 @Valid PageRequestDTO pageRequestDTO) {
        OrderDTO orderDTO = orderService.getUserOrder(id, orderId, pageRequestDTO);
        orderDTOLinkBuilder.toModel(orderDTO);
        return orderDTO;
    }

    @GetMapping("/{id}/most-popular-tag")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO getMostPopularTag(@PathVariable Long id){
        TagDTO tagDTO = tagService.getMostPopularTag(id);
        tagDTOLinkBuilder.toModel(tagDTO);
        return tagDTO;
    }
}
