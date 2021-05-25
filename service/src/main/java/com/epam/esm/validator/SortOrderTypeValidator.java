package com.epam.esm.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SortOrderTypeValidator implements
        ConstraintValidator<SortOrderType, String> {

    private static final String ASC_ORDER_TYPE = "ASC";
    private static final String DESC_ORDER_TYPE = "DESC";

    @Override
    public boolean isValid(String orderType, ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(orderType) && (ASC_ORDER_TYPE.equalsIgnoreCase(orderType) ||
                DESC_ORDER_TYPE.equalsIgnoreCase(orderType));
    }
}
