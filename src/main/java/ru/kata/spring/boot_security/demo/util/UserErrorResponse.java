package ru.kata.spring.boot_security.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class UserErrorResponse {

    private String message;

    private LocalDateTime timestamp;
}
