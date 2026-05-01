package org.example.demo.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.exception.InvalidRegionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRegionException.class)
    public ProblemDetail handleInvalidRegionException(Exception exception) {
        log.error("Handling " + exception.getClass());
        log.error(exception.getMessage());

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST, exception.getMessage());

        problemDetail.setTitle("Invalid region or city id");
        return problemDetail;
    }
}
