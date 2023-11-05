package com.example.vivatech.assignment.controller;

import com.example.vivatech.assignment.Exceptions.OTPException;
import com.example.vivatech.assignment.Exceptions.UserProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({OTPException.class, UserProfileNotFoundException.class})
    public ResponseEntity<String> handleCustomException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
