package com.example.prodreadyfeatures.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
public class ApiError {
    private LocalDateTime timestamp;
    private String error;
    private HttpStatus status;

    public ApiError(String error, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.error = error;
        this.status = status;
    }
}
