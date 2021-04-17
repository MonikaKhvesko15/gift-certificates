package com.epam.esm.validator;

import com.epam.esm.dto.CertificateDTO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CertificateDTOValidator extends DTOValidator<CertificateDTO> {

    @Override
    public boolean isValid(CertificateDTO certificateDTO) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<CertificateDTO>> violations = validator.validate(certificateDTO);
        return violations.isEmpty();
    }
}
