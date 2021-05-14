package com.epam.esm.dto;

import com.epam.esm.validator.RequestField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@RequestField
public class CertificateRequestFieldDTO implements Serializable {
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @Positive
    private BigDecimal price;

    @Max(value = 150)
    @Min(value = 1)
    private Integer duration;
}
