package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagDTOValidator extends Validator<TagDTO> {
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 50;

    @Override
    public boolean isValid(TagDTO tagDTO) {
        boolean result = true;
        if (tagDTO == null) {
            result=false;
            addErrorMessage("Cannot create empty entity.");
        } else {
            String tagName = tagDTO.getName();
            if (tagName == null) {
                result = false;
                addErrorMessage("You cannot add a tag without a required name parameter.");
            } else if (tagName.length() < MIN_NAME_LENGTH || tagName.length() > MAX_NAME_LENGTH) {
                result = false;
                addErrorMessage("Cannot add tag with invalid size name.");
            }
        }
        return result;
    }
}
