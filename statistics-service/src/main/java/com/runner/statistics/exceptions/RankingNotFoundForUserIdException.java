package com.runner.statistics.exceptions;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class RankingNotFoundForUserIdException extends RuntimeException {
    public RankingNotFoundForUserIdException() {
        super("No ranking found for the given user id.");
    }
}
