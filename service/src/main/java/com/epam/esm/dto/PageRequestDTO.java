package com.epam.esm.dto;

import com.epam.esm.validator.PageParams;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@PageParams
public class PageRequestDTO {
    private Object page = 1;
    private Object size = 10;
}
