package com.lms.co.za.exception.handler;

import com.lms.co.za.exception.DuplicateResourceException;
import com.lms.co.za.exception.ResourceNotFoundException;
import com.lms.co.za.exception.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        ApiError apiError = new ApiError();
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setMessage(resourceNotFoundException.getMessage());
        apiError.setRequest(webRequest.getDescription(false));
        return apiError;
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleDuplicateResourceException(DuplicateResourceException duplicateResourceException, WebRequest webRequest){
        ApiError apiError = new ApiError();
        apiError.setCode(HttpStatus.CONFLICT.value());
        apiError.setMessage(duplicateResourceException.getMessage());
        apiError.setRequest(webRequest.getDescription(false));
        return apiError;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException constraintViolationException, WebRequest webRequest){
        ApiError apiError = new ApiError();
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(constraintViolationException.getMessage());
        apiError.setRequest(webRequest.getDescription(false));
        return apiError;

    }
}
