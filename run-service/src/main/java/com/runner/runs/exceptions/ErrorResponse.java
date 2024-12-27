package com.runner.runs.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
