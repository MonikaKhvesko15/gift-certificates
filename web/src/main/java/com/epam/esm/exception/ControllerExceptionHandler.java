package com.epam.esm.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
    public static final String ENTITY_VALIDATOR_EXCEPTION = "entity_not_valid";
    public static final String FIELDS_NUMBER_VALIDATOR_EXCEPTION = "fields_number_not_valid";
    public static final String ACCESS_ERROR = "access_error";
    public static final String INTERNAL_SERVER_ERROR = "server_error";


    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse entityNotFoundHandler(EntityNotFoundException e, WebRequest request) {
        Locale locale = getLocale(request);
        String message = buildMessage(ENTITY_NOT_FOUND, locale) + e.getMessage();
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse entityExistsHandler(EntityAlreadyExistsException e, WebRequest request) {
        Locale locale = getLocale(request);
        String message = buildMessage(ENTITY_ALREADY_EXISTS, locale) + e.getMessage();
        return new ExceptionResponse(HttpStatus.CONFLICT.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse validatorExceptionHandler(BindException e, WebRequest request) {
        Locale locale = getLocale(request);
        BindingResult bindingResult = e.getBindingResult();
        String message = buildValidatorExceptionMessage(bindingResult, locale);
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ExceptionResponse handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Locale locale = getLocale(request);
        String message = buildMessage(ACCESS_ERROR, locale);
        return new ExceptionResponse(HttpStatus.FORBIDDEN.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse serverExceptionHandler(RuntimeException e, WebRequest request) {
        Locale locale = getLocale(request);
        String message = buildMessage(INTERNAL_SERVER_ERROR, locale);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(message));
    }

    private Locale getLocale(WebRequest request) {
        String localeString = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return StringUtils.isNotEmpty(localeString) ? Locale.forLanguageTag(localeString) : Locale.getDefault();
    }

    private String buildMessage(String mainMessage, Locale locale) {
        return messageSource.getMessage(mainMessage, new Object[]{}, locale);
    }

    private String buildValidatorExceptionMessage(BindingResult bindingResult, Locale locale) {
        String resultMessage;
        if (bindingResult.getFieldError() != null) {
            resultMessage = buildMessage(ENTITY_VALIDATOR_EXCEPTION, locale) +
                    Objects.requireNonNull(bindingResult.getFieldError()).getField()+
                    DELIMITER +
                    buildMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), locale);
        } else {
            resultMessage = buildMessage(FIELDS_NUMBER_VALIDATOR_EXCEPTION, locale);
        }
        return resultMessage;
    }
}
