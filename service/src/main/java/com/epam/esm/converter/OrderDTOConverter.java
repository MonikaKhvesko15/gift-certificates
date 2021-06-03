package com.epam.esm.converter;

import com.epam.esm.dto.entityDTO.CertificateDTO;
import com.epam.esm.dto.entityDTO.OrderDTO;
import com.epam.esm.dto.entityDTO.UserDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDTOConverter implements DTOConverter<Order, OrderDTO>{
    private final ModelMapper modelMapper;
    private final CertificateDTOConverter certificateDTOConverter;
    private final UserDTOConverter userDTOConverter;

    @Override
    public Order convertToEntity(OrderDTO orderDTO) {
        List<Certificate> certificates = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderDTO.getCertificates())) {
            certificates = orderDTO.getCertificates().stream()
                    .map(certificateDTOConverter::convertToEntity)
                    .collect(Collectors.toList());
        }
        Order order = modelMapper.map(orderDTO, Order.class);
        UserDTO userDTO = orderDTO.getUser();
        if (userDTO != null) {
            User user = userDTOConverter.convertToEntity(userDTO);
            order.setUser(user);
        }
        order.setCertificates(certificates);
        return order;
    }

    @Override
    public OrderDTO convertToDto(Order order) {
        List<CertificateDTO> certificates = order.getCertificates().stream()
                .map(certificateDTOConverter::convertToDto)
                .collect(Collectors.toList());
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        User user = order.getUser();
        if (user != null) {
            UserDTO userDTO = userDTOConverter.convertToDto(user);
            orderDTO.setUser(userDTO);
        }
        orderDTO.setCertificates(certificates);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> convertToListDTO(List<Order> orders) {
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
