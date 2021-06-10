package com.epam.esm.controller;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.dto.UserRequestFieldDTO;
import com.epam.esm.dto.entityDTO.OrderDTO;
import com.epam.esm.dto.entityDTO.TagDTO;
import com.epam.esm.dto.entityDTO.UserDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.PageDTOLinkBuilder;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

/**
 * The controller to provide CR operations on {@link UserDTO}.
 */
@RestController
@RequestMapping(value = "/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final OrderService orderService;
    private final TagService tagService;
    private final LinkBuilder<UserDTO> userDTOLinkBuilder;
    private final LinkBuilder<OrderDTO> orderDTOLinkBuilder;
    private final LinkBuilder<TagDTO> tagDTOLinkBuilder;
    private final PageDTOLinkBuilder<UserDTO> pageUserDTOLinkBuilder;
    private final PageDTOLinkBuilder<OrderDTO> pageOrderDTOLinkBuilder;

    /**
     * Find by id.
     *
     * @param id the id of user
     * @return the {@link UserDTO} of queried user
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"admin","user"})
    public UserDTO findById(@PathVariable Long id) {
        UserDTO userDTO = userService.getById(id);
        userDTOLinkBuilder.toModel(userDTO);
        return userDTO;
    }

    /**
     * Find all.
     *
     * @return the {@link PageDTO<UserDTO>} of all users
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("admin")
    public PageDTO<UserDTO> findAll(PageRequestDTO pageRequestDTO) {
        PageDTO<UserDTO> pageDTO = userService.findAll(pageRequestDTO);
        if (CollectionUtils.isNotEmpty(pageDTO.getContent())) {
            pageDTO.getContent().forEach(userDTOLinkBuilder::toModel);
            pageUserDTOLinkBuilder.toModel(pageDTO);
        }
        return pageDTO;
    }

    /**
     * Create order.
     *
     * @param id       the id of user
     * @param orderDTO the order of concrete user
     * @return {@link OrderDTO} created order
     */
    @PostMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed({"user"})
    public OrderDTO createOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.create(id, orderDTO);
        orderDTOLinkBuilder.toModel(createdOrder);
        return createdOrder;
    }

    /**
     * Get all user orders.
     *
     * @param id the id of concrete user
     * @return {@link PageDTO} all user orders
     * */
    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"admin","user"})
    public PageDTO<OrderDTO> getAllUserOrders(@PathVariable Long id,
                                              PageRequestDTO pageRequestDTO) {
        PageDTO<OrderDTO> orderDTOPage = orderService.getAllUserOrders(id, pageRequestDTO);
        orderDTOPage.getContent().forEach(orderDTOLinkBuilder::toModel);
        pageOrderDTOLinkBuilder.toModel(orderDTOPage);
        return orderDTOPage;
    }

    /**
     * Get user order.
     *
     * @param id the id of concrete user
     * @param orderId the id of concrete user order
     * @return {@link OrderDTO} user order
     */
    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"admin","user"})
    public OrderDTO getUserOrder(@PathVariable Long id,
                                 @PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getUserOrder(id, orderId);
        orderDTOLinkBuilder.toModel(orderDTO);
        return orderDTO;
    }

    /**
     * Get the most widely used tag of a user with the highest cost of all orders.
     *
     * @param id the id of user
     * @return {@link TagDTO} most popular tag
     */
    @GetMapping("/{id}/most-popular-tag")
    @RolesAllowed({"admin","user"})
    public TagDTO getMostPopularTag(@PathVariable Long id) {
        TagDTO tagDTO = tagService.getMostPopularTag(id);
        if(tagDTO!=null){
            tagDTOLinkBuilder.toModel(tagDTO);
        }
        return tagDTO;
    }

    /**
     * Delete user.
     *
     * @param id the id of user
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("admin")
    public void delete(@PathVariable Long id) {
        userService.remove(id);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"admin","user"})
    public UserDTO update(@RequestBody @Valid UserRequestFieldDTO userRequestFieldDTO,
                                 @PathVariable Long id) {
        UserDTO updatedUser = userService.update(id, userRequestFieldDTO);
        userDTOLinkBuilder.toModel(updatedUser);
        return updatedUser;
    }
}
