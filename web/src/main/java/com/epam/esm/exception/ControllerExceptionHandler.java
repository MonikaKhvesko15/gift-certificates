package com.epam.esm.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Locale;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
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
    public ExceptionResponse entityNotFoundHandler(EntityNotFoundException e, Locale locale) {
        String message = messageSource.getMessage(ENTITY_NOT_FOUND, new Object[]{}, locale) + e.getMessage();
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse entityExistsHandler(EntityAlreadyExistsException e, Locale locale) {
        String message = messageSource.getMessage(ENTITY_ALREADY_EXISTS, new Object[]{}, locale) + e.getMessage();
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse validatorExceptionHandler(MethodArgumentNotValidException e, Locale locale) {
        String message = messageSource.getMessage(VALIDATOR_EXCEPTION, new Object[]{}, locale) + e.getLocalizedMessage();
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationFailure(ConstraintViolationException ex) {
        StringBuilder messages = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            messages.append(violation.getMessage());
        }
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                Collections.singletonList(messages.toString()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse serverExceptionHandler(RuntimeException e, Locale locale) {
        String message = messageSource.getMessage(INTERNAL_SERVER_ERROR, new Object[]{}, locale);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(message));
    }
}
