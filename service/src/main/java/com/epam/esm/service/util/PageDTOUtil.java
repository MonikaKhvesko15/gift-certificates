package com.epam.esm.service.util;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.entity.BaseEntity;
import com.epam.esm.repository.Repository;
import com.epam.esm.specification.CriteriaSpecification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageDTOUtil<E extends BaseEntity, D> {

    private static final int NUMB_FIRST_PAGE = 1;

    public PageDTO<D> fillPageDTO(List<D> content,
                                  PageRequestDTO pageRequestDTO,
                                  CriteriaSpecification<E> specification,
                                  Repository<E> repository) {
        PageDTO<D> pageDTO = new PageDTO<>();
        pageDTO.setContent(content);
        pageDTO.setPageNumber(pageRequestDTO.getPage());
        pageDTO.setPageSize(pageRequestDTO.getSize());
        Integer totalElements = repository.countEntities(specification);
        pageDTO.setTotalElements(totalElements);
        int totalPages = countTotalPages(totalElements, pageRequestDTO.getSize());
        pageDTO.setTotalPages(totalPages);
        boolean isFirst = isFirstPage(pageRequestDTO.getPage());
        pageDTO.setFirst(isFirst);
        boolean isLast = isLastPage(pageRequestDTO.getPage(), totalPages);
        pageDTO.setLast(isLast);
        return pageDTO;
    }

    private int countTotalPages(long totalElements, int pageSize) {
        return (int) ((totalElements / pageSize) + 1);
    }

    private boolean isFirstPage(int currentPage) {
        return currentPage == NUMB_FIRST_PAGE;
    }

    private boolean isLastPage(int currentPage, int totalPages) {
        return currentPage == totalPages;
    }
}
