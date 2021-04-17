package com.epam.esm.validator;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public abstract class DTOValidator<T> {
    protected ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public abstract boolean isValid(T t);
}
