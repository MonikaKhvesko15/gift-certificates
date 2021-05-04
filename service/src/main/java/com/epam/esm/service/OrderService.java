package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO getById(Long id);

    Page<OrderDTO> findAll(Pageable pageable);

    OrderDTO create(Long userId, OrderDTO orderDTO);
}
