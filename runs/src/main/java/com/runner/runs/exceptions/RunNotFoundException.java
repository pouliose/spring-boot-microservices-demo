package com.runner.runs.exceptions;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class RunNotFoundException extends RuntimeException {
    public RunNotFoundException() {
        super("Run not found");
    }
}
