package com.epam.esm.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    private static final String DELIMITER = " ";
    private final MessageSource messageSource;
    public static final String ENTITY_ALREADY_EXISTS = "entity_already_exists";
    public static final String ENTITY_NOT_FOUND = "entity_not_found";
    public static final String VALIDATOR_EXCEPTION = "entity_not_valid";
    public static final String INTERNAL_SERVER_ERROR = "server_error";


    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse entityNotFoundHandler(EntityNotFoundException e, WebRequest request) {
        String localeString = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        Locale locale = localeString != null ? Locale.forLanguageTag(localeString) : Locale.getDefault();
        String message = messageSource.getMessage(ENTITY_NOT_FOUND, new Object[]{}, locale) + e.getMessage();
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse entityExistsHandler(EntityAlreadyExistsException e, WebRequest request) {
        String localeString = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        Locale locale = localeString != null ? Locale.forLanguageTag(localeString) : Locale.getDefault();
        String message = messageSource.getMessage(ENTITY_ALREADY_EXISTS, new Object[]{}, locale) + e.getMessage();
        return new ExceptionResponse(HttpStatus.CONFLICT.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse validatorExceptionHandler(BindException e, WebRequest request) {
        String localeString = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        Locale locale = localeString != null ? Locale.forLanguageTag(localeString) : Locale.getDefault();
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        String errorField = Objects.requireNonNull(bindingResult.getFieldError()).getField();
        String message = messageSource.getMessage(VALIDATOR_EXCEPTION, new Object[]{}, locale) + errorField + DELIMITER
                + messageSource.getMessage(errorMessage, new Object[]{}, locale);
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse serverExceptionHandler(RuntimeException e, WebRequest request) {
        String localeString = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        Locale locale = localeString != null ? Locale.forLanguageTag(localeString) : Locale.getDefault();
        String message = messageSource.getMessage(INTERNAL_SERVER_ERROR, new Object[]{}, locale);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(message));
    }
}
