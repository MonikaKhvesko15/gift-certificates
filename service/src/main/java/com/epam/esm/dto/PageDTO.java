package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageDTO<T> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Integer totalElements;
    private boolean isFirst;
    private boolean isLast;
    private List<T> content;
}

