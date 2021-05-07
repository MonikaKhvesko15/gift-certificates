package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;

public interface OrderService {
    OrderDTO getById(Long id);

    PageDTO<OrderDTO> findAll(PageRequestDTO pageRequestDTO);

    OrderDTO create(Long userId, OrderDTO orderDTO);

    PageDTO<OrderDTO> getAllUserOrders(Long userId, PageRequestDTO pageRequestDTO);
}
