package com.epam.esm.validator;

import com.epam.esm.dto.CertificateDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CertificateDTOValidator extends Validator<CertificateDTO> {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_DESCRIPTION_LENGTH = 700;
    private static final int MIN_DESCRIPTION_LENGTH = 1;
    private static final int MIN_PRICE_VALUE = 10;
    private static final int MAX_PRICE_VALUE = 1_000_000_000;
    private static final int MIN_DURATION_VALUE = 1;
    private static final int MAX_DURATION_VALUE = 150;

    @Override
    public boolean isValid(CertificateDTO certificateDTO) {
        boolean result;
        if (certificateDTO == null) {
            result = false;
            addErrorMessage("Cannot create empty entity.");
        } else {
            result = checkName(certificateDTO.getName())
                    && checkDescription(certificateDTO.getDescription())
                    && checkPrice(certificateDTO.getPrice())
                    && checkDuration(certificateDTO.getDuration());
        }
        return result;
    }

    private boolean checkName(String name) {
        boolean result = true;
        if (name == null) {
            addErrorMessage("Cannot add certificate with empty name.");
            result = false;
        } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            addErrorMessage("Cannot add certificate with invalid size name.");
            result = false;
        }
        return result;
    }

    private boolean checkDescription(String description) {
        boolean result = true;
        if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
            addErrorMessage("Cannot add certificate with invalid size description.");
            result = false;
        }
        return result;
    }

    private boolean checkPrice(BigDecimal price) {
        boolean result = true;
        if (price == null) {
            addErrorMessage("Cannot add certificate with empty price.");
            result = false;
        } else if (price.compareTo(BigDecimal.valueOf(MIN_PRICE_VALUE)) < 1 || price.compareTo(BigDecimal.valueOf(MAX_PRICE_VALUE)) > -1) {
            addErrorMessage("Cannot add certificate with invalid size price.");
            result = false;
        }
        return result;
    }

    private boolean checkDuration(Integer duration) {
        boolean result = true;
        if (duration == null) {
            addErrorMessage("Cannot add certificate with empty duration.");
            result = false;
        } else if (duration < MIN_DURATION_VALUE || duration > MAX_DURATION_VALUE) {
            addErrorMessage("Cannot add certificate with invalid size duration.");
            result = false;
        }
        return result;
    }
}
