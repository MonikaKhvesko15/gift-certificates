package com.epam.esm.validator;

import com.epam.esm.dto.PageRequestDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PageParamsValidator implements
        ConstraintValidator<PageParams, PageRequestDTO> {
    private static final String DIGITAL_REGEX = "[0-9]+";
    private static final int MAX_PAGE_NUMBER = 1000;
    private static final int MIN_PAGE_NUMBER = 1;
    private static final int MAX_PAGE_SIZE = 100;
    private static final int MIN_PAGE_SIZE = 1;

    @Override
    public boolean isValid(PageRequestDTO pageRequest, ConstraintValidatorContext context) {
        if (isOnlyDigits(pageRequest.getPage().toString()) ||
                Integer.parseInt(pageRequest.getPage().toString()) < MIN_PAGE_NUMBER) {
            pageRequest.setPage(MIN_PAGE_NUMBER);
        }

        if (Integer.parseInt(pageRequest.getPage().toString()) > MAX_PAGE_NUMBER) {
            pageRequest.setPage(MAX_PAGE_NUMBER);
        }

        if (isOnlyDigits(pageRequest.getSize().toString()) ||
                Integer.parseInt(pageRequest.getSize().toString()) < MIN_PAGE_SIZE) {
            pageRequest.setSize(MIN_PAGE_SIZE);
        }

        if (Integer.parseInt(pageRequest.getSize().toString()) > MAX_PAGE_SIZE) {
            pageRequest.setSize(MAX_PAGE_SIZE);
        }

        return true;
    }

    public boolean isOnlyDigits(String str) {
        return !Pattern.compile(DIGITAL_REGEX).matcher(str).find();
    }
}
