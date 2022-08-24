package com.lms.co.za.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @Schema(description = "Http status code")
    private int code;
    @Schema(description = "Error message")
    private String message;
    @Schema(description = "Web request")
    private String request;
    @Schema(description = "Time of request")
    private LocalDateTime timestamp = LocalDateTime.now();
}
