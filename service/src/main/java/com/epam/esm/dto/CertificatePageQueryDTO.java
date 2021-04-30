package com.epam.esm.dto;

import com.epam.esm.validator.OrderType;
import com.epam.esm.validator.SortType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CertificatePageQueryDTO implements Serializable {
    @Size(max = 50)
    private String tagName;

    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @SortType
    private String sortBy = "name";

    @OrderType
    private String order = "asc";
}
