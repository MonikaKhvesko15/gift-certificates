package com.epam.esm.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderTypeValidator implements
        ConstraintValidator<OrderType, String> {

    private static final String ASC_ORDER_TYPE = "ASC";
    private static final String DESC_ORDER_TYPE = "DESC";

    @Override
    public void initialize(OrderType orderType) {

    }

    @Override
    public boolean isValid(String orderType, ConstraintValidatorContext context) {
        return !orderType.isEmpty() && (orderType.equalsIgnoreCase(ASC_ORDER_TYPE) ||
                orderType.equalsIgnoreCase(DESC_ORDER_TYPE));
    }
}
