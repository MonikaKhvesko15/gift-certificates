package com.epam.esm.dto;

import com.epam.esm.entity.OrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderDTO extends RepresentationModel<OrderDTO> implements Serializable {
    private Long id;
    private BigDecimal totalPrice;
    private LocalDateTime createDate;
    private OrderStatus status;
    private List<CertificateDTO> certificates;
    private UserDTO user;
}
