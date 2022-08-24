package com.lms.co.za.exception.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private int code;
    private String message;
    private String request;
    private LocalDateTime timestamp = LocalDateTime.now();

}
