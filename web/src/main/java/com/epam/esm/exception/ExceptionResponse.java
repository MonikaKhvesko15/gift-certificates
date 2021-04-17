package com.epam.esm.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class ExceptionResponse {
    private final int code;
    private final List<String> messages;

    public ExceptionResponse(int code, List<String> messages) {
        this.code = code;
        this.messages = new ArrayList<>(messages);
    }

    public int getCode() {
        return code;
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public String
    toString() {
        return new StringJoiner(". ", "[", "]")
                .add("errorCode: " + code)
                .add("error messages:" + messages)
                .toString();
    }
}
