package com.epam.esm.util;

import com.epam.esm.dto.PageRequestDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PageRequestDTOHandler {
    private static final String DIGITAL_REGEX = "[0-9]+";
    private static final int MAX_PAGE_NUMBER = 1000;
    private static final int MIN_PAGE_NUMBER = 1;
    private static final int MAX_PAGE_SIZE = 100;
    private static final int MIN_PAGE_SIZE = 1;

    public PageRequestDTO checkPageRequest(PageRequestDTO pageRequest) {

        Object sizeObj = pageRequest.getSize();
        Object pageObj = pageRequest.getPage();

        if (isOnlyDigits(pageObj.toString()) ||
                Integer.parseInt(pageObj.toString()) < MIN_PAGE_NUMBER) {
            pageObj = MIN_PAGE_NUMBER;
        }

        if (Integer.parseInt(pageObj.toString()) > MAX_PAGE_NUMBER) {
            pageObj = MAX_PAGE_NUMBER;
        }

        if (isOnlyDigits(sizeObj.toString()) ||
                Integer.parseInt(sizeObj.toString()) < MIN_PAGE_SIZE) {
            sizeObj = MIN_PAGE_SIZE;
        }

        if (Integer.parseInt(sizeObj.toString()) > MAX_PAGE_SIZE) {
            sizeObj = MAX_PAGE_SIZE;
        }
        return new PageRequestDTO(pageObj, sizeObj);
    }

    private boolean isOnlyDigits(String str) {
        return !Pattern.compile(DIGITAL_REGEX).matcher(str).find();
    }
}
