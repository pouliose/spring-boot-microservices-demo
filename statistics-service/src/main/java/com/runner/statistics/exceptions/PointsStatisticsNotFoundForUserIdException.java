package com.runner.statistics.exceptions;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class PointsStatisticsNotFoundForUserIdException extends RuntimeException {
    public PointsStatisticsNotFoundForUserIdException() {
        super("No points statistics found for the given user id.");
    }
}
