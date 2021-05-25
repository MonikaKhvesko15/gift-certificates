package com.epam.esm.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SortTypeValidation implements ConstraintValidator<SortType, String> {
    private static final String DATE_SORT_TYPE = "DATE";
    private static final String NAME_SORT_TYPE = "NAME";

    @Override
    public boolean isValid(String sortType, ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(sortType) && (NAME_SORT_TYPE.equalsIgnoreCase(sortType) ||
                DATE_SORT_TYPE.equalsIgnoreCase(sortType));
    }
}
