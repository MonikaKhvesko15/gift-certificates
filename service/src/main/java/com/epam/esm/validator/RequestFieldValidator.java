package com.epam.esm.validator;

import com.epam.esm.dto.CertificateRequestFieldDTO;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequestFieldValidator implements
        ConstraintValidator<RequestField, CertificateRequestFieldDTO> {

    private static final int ONE_FIELD = 1;

    @Override
    public boolean isValid(CertificateRequestFieldDTO value, ConstraintValidatorContext context) {
        int notEmptyFields = 0;
        if (StringUtils.isNotEmpty(value.getName())) {
            ++notEmptyFields;
        }
        if (StringUtils.isNotEmpty(value.getDescription())) {
            ++notEmptyFields;
        }
        if (value.getPrice() != null) {
            ++notEmptyFields;
        }
        if (value.getDuration() != null) {
            ++notEmptyFields;
        }
        return notEmptyFields == ONE_FIELD;
    }
}
