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
    public static final String ENTITY_ALREADY_EXISTS = "entity.already_exists";
   // public static final String ENTITY_NOT_FOUND = "entity.not_found";
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";


    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ExceptionResponse> handleEntityNotFound(EntityNotFoundException e, Locale locale) {
//        String message = messageSource.getMessage(ENTITY_NOT_FOUND, new Object[]{}, locale);
//        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), Collections.singletonList(message));
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEntityExists(EntityAlreadyExistsException e, Locale locale) {
        String message = messageSource.getMessage(ENTITY_ALREADY_EXISTS, new Object[]{}, locale);
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(message));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException e, Locale locale) {
        String message = messageSource.getMessage(INTERNAL_SERVER_ERROR, new Object[]{}, locale);
        ExceptionResponse response = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(message));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
