package org.example.measurementapp.exception;

/**
 * Exception thrown when monthly report generation fails.
 */
public class StatsMonthlyReportGenerationException extends RuntimeException {

    /**
     * Constructor with generic message.
     */
    public StatsMonthlyReportGenerationException() {
        super("Failed to generate monthly report");
    }
}
