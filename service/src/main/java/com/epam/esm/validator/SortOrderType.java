package com.epam.esm.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;


@Documented
@Constraint(validatedBy = SortOrderTypeValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortOrderType {
    String message() default "validation.sort.order.message";

    Class[] groups() default {};

    Class[] payload() default {};
}
