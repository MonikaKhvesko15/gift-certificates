package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class TagDTOValidator extends DTOValidator<TagDTO> {
    @Override
    public boolean isValid(TagDTO tagDTO) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);
        return violations.isEmpty();
    }
}