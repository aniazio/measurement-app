package org.example.demo.exception;

public class StatsMonthlyReportGenerationException extends RuntimeException {

    public StatsMonthlyReportGenerationException() {
        super("Failed to generate monthly report");
    }
}
