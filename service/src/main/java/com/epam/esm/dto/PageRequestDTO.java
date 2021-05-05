package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageRequestDTO {
    @Positive
    private Integer page = 1;
    @Positive
    private Integer size = 10;
}
