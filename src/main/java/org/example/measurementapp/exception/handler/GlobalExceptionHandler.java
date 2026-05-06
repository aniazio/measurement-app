package org.example.measurementapp.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.measurementapp.exception.ExternalDependencyException;
import org.example.measurementapp.exception.InvalidRegionException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    protected static final String INVALID_REGION_OR_CITY_ID = "Invalid region or city id";
    protected static final String MEASUREMENT_ALREADY_RECORDED = "Measurement already recorded";

    /**
     * Handles {@link InvalidRegionException}.
     *
     * @param exception invalid region exception
     * @return response for invalid region exception
     */
    @ExceptionHandler(InvalidRegionException.class)
    public ProblemDetail handleInvalidRegionException(InvalidRegionException exception) {
        log.error("Handling " + exception.getClass());
        log.error(exception.getMessage());

        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, INVALID_REGION_OR_CITY_ID);
    }

    /**
     * Handles {@link DuplicateKeyException}.
     *
     * @param exception duplicated key exception
     * @return response for duplicated key exception
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ProblemDetail handleDuplicateKeyException(DuplicateKeyException exception) {
        log.error("Handling " + exception.getClass());
        log.error(exception.getMessage());

        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, MEASUREMENT_ALREADY_RECORDED);
    }

    /**
     * Handles {@link ExternalDependencyException}
     *
     * @param exception external dependency exception
     * @return response for external dependency exception
     */
    @ExceptionHandler(ExternalDependencyException.class)
    public ProblemDetail handleExternalDependencyException(ExternalDependencyException exception) {
        log.error("Handling " + exception.getClass());
        log.error(exception.getMessage());

        return ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
    }

    /**
     * Handles {@link MethodArgumentNotValidException}
     *
     * @param exception method argument not valid exception
     * @return response with exception message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Handling " + exception.getClass());
        log.error(exception.getMessage());

        List<String> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, String.join("; ", errors));
        problemDetail.setTitle("Validation failed");
        return problemDetail;
    }


}
