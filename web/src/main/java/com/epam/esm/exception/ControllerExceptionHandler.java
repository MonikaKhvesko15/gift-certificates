package com.epam.esm.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Locale;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final MessageSource messageSource;
    public static final String ENTITY_ALREADY_EXISTS = "entity_already_exists";
    public static final String ENTITY_NOT_FOUND = "entity_not_found";
    //public static final String VALIDATOR_EXCEPTION = "entity_not_valid";
    public static final String INTERNAL_SERVER_ERROR = "server_error";


    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> entityNotFoundHandler(EntityNotFoundException e, Locale locale) {
        String message = messageSource.getMessage(ENTITY_NOT_FOUND, new Object[]{}, locale) + e.getMessage();
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), Collections.singletonList(message));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> entityExistsHandler(EntityAlreadyExistsException e, Locale locale) {
        String message = messageSource.getMessage(ENTITY_ALREADY_EXISTS, new Object[]{}, locale) + e.getMessage();
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(message));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<ExceptionResponse> validatorExceptionHandler(ValidatorException e, Locale locale) {
        //String message = messageSource.getMessage(VALIDATOR_EXCEPTION, new Object[]{}, locale);
        String message = e.getMessage();
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(message));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> serverExceptionHandler(RuntimeException e, Locale locale) {
        String message = messageSource.getMessage(INTERNAL_SERVER_ERROR, new Object[]{}, locale);
        ExceptionResponse response = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(message));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
