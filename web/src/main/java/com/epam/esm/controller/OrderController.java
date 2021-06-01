package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.link.PageDTOLinkBuilder;
import com.epam.esm.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller to provide operations on {@link OrderDTO}.
 */
@RestController
@RequestMapping(value = "/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    private final LinkBuilder<OrderDTO> orderDTOLinkBuilder;
    private final PageDTOLinkBuilder<OrderDTO> pageDTOLinkBuilder;

    /**
     * Find by id.
     *
     * @param id the id of order
     * @return the {@link OrderDTO} of queried order
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public OrderDTO findById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.getById(id);
        orderDTOLinkBuilder.toModel(orderDTO);
        return orderDTO;
    }

    /**
     * Find all orders.
     *
     * @return the {@link PageDTO<OrderDTO>} of queried orders
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public PageDTO<OrderDTO> findAll(PageRequestDTO pageRequestDTO) {
        PageDTO<OrderDTO> pageDTO = orderService.findAll(pageRequestDTO);
        if(CollectionUtils.isNotEmpty(pageDTO.getContent())){
            pageDTO.getContent().forEach(orderDTOLinkBuilder::toModel);
            pageDTOLinkBuilder.toModel(pageDTO);
        }
        return pageDTO;
    }
}
