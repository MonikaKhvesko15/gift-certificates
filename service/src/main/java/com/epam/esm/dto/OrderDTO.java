package com.epam.esm.dto;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.OrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
    @NotNull
    @Positive
    private BigDecimal totalPrice;
    private LocalDateTime createDate;
    private OrderStatus status;
    @Valid
    private List<CertificateDTO> certificates;
}
