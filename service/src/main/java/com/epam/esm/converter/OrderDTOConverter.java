package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDTOConverter {
    private final ModelMapper modelMapper;
    private final CertificateDTOConverter certificateDTOConverter;

    @Autowired
    public OrderDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.certificateDTOConverter = new CertificateDTOConverter(modelMapper);
    }

    public Order convertToEntity(OrderDTO orderDTO) {
        List<Certificate> certificates = new ArrayList<>();
        if (!orderDTO.getCertificates().isEmpty()) {
            certificates = orderDTO.getCertificates().stream().map(certificateDTOConverter::convertToEntity).collect(Collectors.toList());
        }
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setCertificates(certificates);
        return order;
    }

    public OrderDTO convertToDto(Order order) {
        List<CertificateDTO> certificates = order.getCertificates().stream().map(certificateDTOConverter::convertToDto).collect(Collectors.toList());
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setCertificates(certificates);
        return orderDTO;
    }

    public List<OrderDTO> convertToListDTO(List<Order> orders) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orders.forEach(order -> {
            OrderDTO orderDTO = convertToDto(order);
            orderDTOList.add(orderDTO);
        });
        return orderDTOList;
    }
}
