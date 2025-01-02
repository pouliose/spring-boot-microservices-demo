package com.runner.notification.exceptions;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException() {
        super("No notification exists for the given id.");
    }
}
