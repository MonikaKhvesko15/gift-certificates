package com.epam.esm.service.impl;

import com.epam.esm.converter.DTOConverter;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderStatus;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.OrderService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.order.AllUserOrdersSpecification;
import com.epam.esm.specification.order.OrderAllSpecification;
import com.epam.esm.specification.order.UserOrderSpecification;
import com.epam.esm.util.PageRequestDTOHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends AbstractService<OrderDTO, Order> implements OrderService {
    private final Repository<User> userRepository;
    private final Repository<Certificate> certificateRepository;

    public OrderServiceImpl(DTOConverter<Order, OrderDTO> converter,
                            Repository<Order> repository,
                            Repository<User> userRepository,
                            PageRequestDTOHandler parser,
                            Repository<Certificate> certificateRepository) {
        super(converter, repository, parser);
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public PageDTO<OrderDTO> findAll(PageRequestDTO pageRequestDTO) {
        PageRequestDTO pageParsed = pageHandler.checkPageRequest(pageRequestDTO);
        CriteriaSpecification<Order> specification = new OrderAllSpecification();
        return getOrderDTOPageDTO(pageParsed, specification);
    }

    @Override
    public OrderDTO create(Long userId, OrderDTO orderDTO) {
        User user = getUserIfExists(userId);
        Order order = converter.convertToEntity(orderDTO);
        order.setUser(user);
        List<Certificate> fullCertificates = getFullCertificates(order);
        order.setCertificates(fullCertificates);
        order.setCreateDate(LocalDateTime.now());
        BigDecimal totalPrice = countTotalPrice(order);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);
        return converter.convertToDto(
                repository.save(order)
        );
    }

    @Override
    public PageDTO<OrderDTO> getAllUserOrders(Long userId, PageRequestDTO pageRequestDTO) {
        PageRequestDTO pageParsed = pageHandler.checkPageRequest(pageRequestDTO);
        User user = getUserIfExists(userId);
        CriteriaSpecification<Order> specification = new AllUserOrdersSpecification(user);
        return getOrderDTOPageDTO(pageParsed, specification);
    }

    private PageDTO<OrderDTO> getOrderDTOPageDTO(PageRequestDTO pageRequestDTO,
                                                 CriteriaSpecification<Order> specification) {
        PageRequestDTO pageParsed = pageHandler.checkPageRequest(pageRequestDTO);
        List<Order> userOrderList = repository.getEntityListBySpecification(specification,
                Integer.parseInt(pageParsed.getPage().toString()),
                Integer.parseInt(pageParsed.getSize().toString()));
        List<OrderDTO> userOrderDTOList = converter.convertToListDTO(userOrderList);
        long totalElements = repository.countEntities(specification);
        return new PageDTO<>(
                Integer.parseInt(pageParsed.getPage().toString()),
                Integer.parseInt(pageParsed.getSize().toString()),
                totalElements,
                userOrderDTOList
        );
    }

    @Override
    public OrderDTO getUserOrder(Long userId, Long orderId) {
        User user = getUserIfExists(userId);
        CriteriaSpecification<Order> specification = new UserOrderSpecification(user, orderId);
        Order order = repository.getEntityBySpecification(specification)
                .orElseThrow(() -> new EntityNotFoundException(" (orderId = " + orderId + ")"));
        return converter.convertToDto(order);
    }

    private User getUserIfExists(Long userId) {
        return userRepository.getById(userId)
                .orElseThrow(() -> new EntityNotFoundException(" (userId = " + userId + ")"));
    }

    private List<Certificate> getFullCertificates(Order order) {
        return order.getCertificates().stream()
                .map(certificate -> certificateRepository.getById(certificate.getId())
                        .orElseThrow(() -> new EntityNotFoundException(" (certificateId = " + certificate.getId() + ")")))
                .collect(Collectors.toList());
    }

    private BigDecimal countTotalPrice(Order order) {
        return order.getCertificates().stream()
                .map(Certificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
