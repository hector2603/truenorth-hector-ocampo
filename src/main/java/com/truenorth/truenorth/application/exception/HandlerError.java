package com.truenorth.truenorth.application.exception;


import com.truenorth.truenorth.infraestructure.controller.dto.response.ErrorResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerError extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger(HandlerError.class);


    private static final ConcurrentHashMap<String, Integer> STATE_CODE = new ConcurrentHashMap<>();

    public HandlerError() {
        STATE_CODE.put(ExceptionLengthValue.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        STATE_CODE.put(ExceptionInvalidValue.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        STATE_CODE.put(ExceptionNullValue.class.getSimpleName(), HttpStatus.NOT_FOUND.value());
        STATE_CODE.put(ExceptionMandatoryValue.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        STATE_CODE.put(BadCredentialsException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        STATE_CODE.put(AccessDeniedException.class.getSimpleName(), HttpStatus.FORBIDDEN.value());
        STATE_CODE.put(TecnicalException.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        STATE_CODE.put(InternalAuthenticationServiceException.class.getSimpleName(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<MessageResponse> handleAllExceptions(Exception exception) {
        ResponseEntity<MessageResponse> result;

        String exceptionName = exception.getClass().getSimpleName();
        String message = exception.getMessage();
        Integer code = STATE_CODE.get(exceptionName);
        if (code != null && code == HttpStatus.UNAUTHORIZED.value()) {
            message = ErrorCode.EC0005.message;
        }
        if (code != null) {
            MessageResponse error = new MessageResponse(null, new ErrorResponse(exceptionName, message));
            result = new ResponseEntity<>(error, HttpStatus.valueOf(code));
        } else {
            LOGGER_ERROR.error(exceptionName, exception);
            MessageResponse error = new MessageResponse(null, new ErrorResponse(exceptionName, ErrorCode.EC0004.message));
            result = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(new MessageResponse(null, new ErrorResponse(ExceptionInvalidValue.class.getSimpleName(), errors)), HttpStatus.BAD_REQUEST);
    }
}