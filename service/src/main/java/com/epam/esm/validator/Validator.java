package com.epam.esm.validator;

import java.util.ArrayList;
import java.util.List;

public abstract class Validator<T> {
    private List<String> errorMessages = new ArrayList<>();

    public abstract boolean isValid(T t);

    public String getErrorMessage() {
        return String.join(". ", errorMessages);
    }

    void addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public void cleanErrorMessages() {
        errorMessages = new ArrayList<>();
    }
}
