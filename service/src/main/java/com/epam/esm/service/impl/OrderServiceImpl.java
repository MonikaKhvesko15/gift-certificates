package com.epam.esm.service.impl;

import com.epam.esm.converter.OrderDTOConverter;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepositoryImpl;
import com.epam.esm.repository.OrderRepositoryImpl;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.UserRepositoryImpl;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.util.PageDTOUtil;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.order.AllUserOrdersSpecification;
import com.epam.esm.specification.order.OrderAllSpecification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final Repository<Order> orderRepository;
    private final Repository<User> userRepository;
    private final Repository<Certificate> certificateRepository;
    private final OrderDTOConverter converter;
    private final PageDTOUtil<Order, OrderDTO> pageDTOUtil;


    public OrderServiceImpl(OrderRepositoryImpl orderRepository,
                            UserRepositoryImpl userRepository,
                            CertificateRepositoryImpl certificateRepository,
                            OrderDTOConverter converter,
                            PageDTOUtil<Order, OrderDTO> pageDTOUtil) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.converter = converter;
        this.pageDTOUtil = pageDTOUtil;
    }

    @Override
    public OrderDTO getById(Long id) {
        Order order = orderRepository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));

        return converter.convertToDto(order);
    }

    @Override
    public PageDTO<OrderDTO> findAll(PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<Order> specification = new OrderAllSpecification();
        List<Order> orderList = orderRepository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<OrderDTO> orderDTOList = converter.convertToListDTO(orderList);
        return pageDTOUtil.fillPageDTO(orderDTOList,
                pageRequestDTO, specification, orderRepository);
    }

    @Override
    public OrderDTO create(Long userId, OrderDTO orderDTO) {
        User user = getUserIfExists(userId);
        Order order = converter.convertToEntity(orderDTO);
        order.setUser(user);
        List<Certificate> fullCertificates = getFullCertificates(order);
        order.setCertificates(fullCertificates);
        BigDecimal totalPrice = countTotalPrice(order);
        order.setTotalPrice(totalPrice);
        return converter.convertToDto(
                orderRepository.save(order)
        );
    }

    @Override
    public PageDTO<OrderDTO> getAllUserOrders(Long userId, PageRequestDTO pageRequestDTO) {
        User user = getUserIfExists(userId);
        CriteriaSpecification<Order> specification = new AllUserOrdersSpecification(user);
        List<Order> userOrderList = orderRepository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<OrderDTO> userOrderDTOList = converter.convertToListDTO(userOrderList);
        return pageDTOUtil.fillPageDTO(userOrderDTOList,
                pageRequestDTO, specification, orderRepository);
    }

    private User getUserIfExists(Long userId) {
        return userRepository.getById(userId)
                .orElseThrow(() -> new EntityNotFoundException(" (userId = " + userId + ")"));
    }

    private List<Certificate> getFullCertificates(Order order) {
        List<Certificate> fullCertificates = new ArrayList<>();
        for (Certificate certificate : order.getCertificates()) {
            Certificate fullCertificate = certificateRepository.getById(certificate.getId())
                    .orElseThrow(() -> new EntityNotFoundException(" (certificateId = " + certificate.getId() + ")"));
            fullCertificates.add(fullCertificate);
        }
        return fullCertificates;
    }

    private BigDecimal countTotalPrice(Order order) {
        return order.getCertificates().stream()
                .map(Certificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
    }
}
