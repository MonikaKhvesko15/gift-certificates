package com.epam.esm.service.impl;

import com.epam.esm.converter.OrderDTOConverter;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderStatus;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.OrderRepositoryImpl;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.UserRepositoryImpl;
import com.epam.esm.service.OrderService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.order.OrderAllSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    Repository<Order> orderRepository;
    Repository<User> userRepository;
    OrderDTOConverter orderDTOConverter;

    @Autowired
    public OrderServiceImpl(OrderRepositoryImpl orderRepository,
                            UserRepositoryImpl userRepository,
                            OrderDTOConverter orderDTOConverter) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderDTOConverter = orderDTOConverter;
    }

    @Override
    public OrderDTO getById(Long id) {
        Order order = orderRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));

        return orderDTOConverter.convertToDto(order);
    }

    @Override
    public List<OrderDTO> findAll(PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<Order> specification = new OrderAllSpecification();
        List<Order> orderList = orderRepository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        return orderDTOConverter.convertToListDTO(orderList);
    }

    @Override
    public OrderDTO create(Long userId, OrderDTO orderDTO) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + userId + ")"));
        Order order = orderDTOConverter.convertToEntity(orderDTO);
        order.setUser(user);
        //todo: count total price
        order.setTotalPrice(BigDecimal.valueOf(12.3));
        //todo: get certificates from db


        order.setStatus(OrderStatus.PENDING);
        return orderDTOConverter.convertToDto(
                orderRepository.save(order)
        );
    }
}
