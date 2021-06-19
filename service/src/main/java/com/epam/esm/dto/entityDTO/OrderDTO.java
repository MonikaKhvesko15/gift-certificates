package com.epam.esm.dto.entityDTO;

import com.epam.esm.entity.OrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends EntityDTO {
    private BigDecimal totalPrice;
    private LocalDateTime createDate;
    private OrderStatus status;
    private List<CertificateDTO> certificates;
    private UserDTO user;
}
