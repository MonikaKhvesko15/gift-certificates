package com.epam.esm.validator;

import com.epam.esm.dto.query.CertificatePageQueryDTO;
import org.springframework.stereotype.Component;

@Component
public class CertificatePageQueryDTOValidator extends Validator<CertificatePageQueryDTO> {
    private static final int MAX_TAG_NAME_LENGTH = 50;
    private static final int MAX_CONTEXT_LENGTH = 700;

    @Override
    public boolean isValid(CertificatePageQueryDTO pageQueryDTO) {
        return checkTagName(pageQueryDTO.getTagName())
                && checkContext(pageQueryDTO.getContext())
                && checkSort(pageQueryDTO.getSortBy())
                && checkOrder(pageQueryDTO.getOrder());
    }

    private boolean checkTagName(String name) {
        boolean result = true;
        if (name.length() > MAX_TAG_NAME_LENGTH) {
            addErrorMessage("Invalid size tag name.");
            result = false;
        }
        return result;
    }

    private boolean checkContext(String context) {
        boolean result = true;
        if (context.length() > MAX_CONTEXT_LENGTH) {
            addErrorMessage("Cannot add certificate with invalid size description.");
            result = false;
        }
        return result;
    }

    private boolean checkSort(String sort) {
        boolean result = true;
        if (!sort.equalsIgnoreCase("NAME") && !sort.equalsIgnoreCase("DATE") && !sort.isEmpty()) {
            addErrorMessage("Invalid sort parameter.");
            result = false;
        }
        return result;
    }

    private boolean checkOrder(String order) {
        boolean result = true;
        if (!order.equalsIgnoreCase("ASC") && !order.equalsIgnoreCase("DESC") && !order.isEmpty()) {
            addErrorMessage("Invalid order sort parameter.");
            result = false;
        }
        return result;
    }
}
