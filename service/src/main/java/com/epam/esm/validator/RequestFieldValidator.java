package com.epam.esm.validator;

import com.epam.esm.dto.CertificateRequestFieldDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequestFieldValidator implements
        ConstraintValidator<RequestField, CertificateRequestFieldDTO> {

    private static final int ONE_FIELD = 1;

    @Override
    public boolean isValid(CertificateRequestFieldDTO value, ConstraintValidatorContext context) {
        int flag = 0;
        if (value.getName() != null) {
            ++flag;
        }
        if (value.getDescription() != null) {
            ++flag;
        }
        if (value.getPrice() != null) {
            ++flag;
        }
        if (value.getDuration() != null) {
            ++flag;
        }
        return flag == ONE_FIELD;
    }
}
