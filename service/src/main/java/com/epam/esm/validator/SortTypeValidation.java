package com.epam.esm.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SortTypeValidation implements ConstraintValidator<SortType, String> {
    private static final String DATE_SORT_TYPE = "DATE";
    private static final String NAME_SORT_TYPE = "NAME";

    @Override
    public void initialize(SortType sortType) {

    }

    @Override
    public boolean isValid(String sortType, ConstraintValidatorContext context) {
        return !sortType.isEmpty() && (sortType.equalsIgnoreCase(NAME_SORT_TYPE) ||
                sortType.equalsIgnoreCase(DATE_SORT_TYPE));
    }
}
