package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageRequestDTO {
    private Object page = 1;
    private Object size = 10;

    public PageRequestDTO(Object page, Object size) {
        this.page = page;
        this.size = size;
    }
}
