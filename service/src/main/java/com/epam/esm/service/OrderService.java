package com.epam.esm.service;

import com.epam.esm.dto.entityDTO.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;

public interface OrderService {
    PageDTO<OrderDTO> findAll(PageRequestDTO pageRequestDTO);

    OrderDTO create(Long userId, OrderDTO orderDTO);

    PageDTO<OrderDTO> getAllUserOrders(Long userId, PageRequestDTO pageRequestDTO);

    OrderDTO getUserOrder(Long userId, Long orderId);
}
