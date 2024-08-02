package ru.kata.spring.boot_security.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<UserErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userErrorResponse);
    }
}
