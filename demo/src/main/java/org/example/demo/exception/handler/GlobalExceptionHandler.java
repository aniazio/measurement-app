package org.example.demo.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.exception.InvalidRegionException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRegionException.class)
    public ProblemDetail handleInvalidRegionException(InvalidRegionException exception) {
        log.error("Handling " + exception.getClass());
        log.error(exception.getMessage());

        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Invalid region or city id");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ProblemDetail handleDuplicateKeyException(DuplicateKeyException exception) {
        log.error("Handling " + exception.getClass());
        log.error(exception.getMessage());

        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Measurement already recorded");
    }
}
