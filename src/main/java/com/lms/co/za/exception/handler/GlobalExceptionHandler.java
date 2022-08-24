package com.lms.co.za.exception.handler;

import com.lms.co.za.exception.ResourceNotFoundException;
import com.lms.co.za.exception.model.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        logger.warn("resourceNotFoundException: " + resourceNotFoundException.getMessage() + ", request: " + webRequest.getDescription(false));
        return new ApiError(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage(), webRequest.getDescription(false), LocalDateTime.now());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleDuplicateResourceException(DataIntegrityViolationException dataIntegrityViolationException, WebRequest webRequest){
        logger.error("dataIntegrityViolationException: " + dataIntegrityViolationException.getMessage() + ", request: " + webRequest.getDescription(false));
        return new ApiError(HttpStatus.CONFLICT.value(), dataIntegrityViolationException.getMessage(),webRequest.getDescription(false), LocalDateTime.now());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException constraintViolationException, WebRequest webRequest){
        logger.error("constraintViolationException: " + constraintViolationException.getMessage() + ", request: " + webRequest.getDescription(false));
        return new ApiError(HttpStatus.BAD_REQUEST.value(), constraintViolationException.getMessage(), webRequest.getDescription(false), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    public ApiError handleAllExceptions(Exception exception, WebRequest webRequest){
        logger.error("exception: " + exception.getMessage() + ", request: " + webRequest.getDescription(false));
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        final HttpStatus httpStatus = responseStatus  != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ApiError(httpStatus.value(), exception.getMessage(), webRequest.getDescription(false), LocalDateTime.now());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        logger.error("methodArgumentNotValidException: " + methodArgumentNotValidException.getMessage() + ", request: " + webRequest.getDescription(false));
        return ResponseEntity.unprocessableEntity().body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(), methodArgumentNotValidException.getAllErrors(), webRequest.getDescription(false), LocalDateTime.now()));

    }
}