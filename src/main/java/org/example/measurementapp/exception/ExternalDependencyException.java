package org.example.measurementapp.exception;

/**
 * Exception thrown when the external service is not available.
 */
public class ExternalDependencyException extends RuntimeException {

    /**
     * Default constructor.
     */
    public ExternalDependencyException() {
        super("External service is not available. Try again later");
    }
}
