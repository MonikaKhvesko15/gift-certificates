package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageRequestDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getById(Long id);

    List<OrderDTO> findAll(PageRequestDTO pageRequestDTO);

    OrderDTO create(Long userId, OrderDTO orderDTO);
}
