package org.example.demo.job;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.exception.StatsMonthlyReportGenerationException;
import org.example.demo.model.RegionDto;
import org.example.demo.model.Stats;
import org.example.demo.repository.StatsRepository;
import org.example.demo.service.RegionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Month summary processor. It generates monthly report with worst cities based on NO2 levels.
 * Job runs on the first day of the month. It analyzes the last month's data and generates a report.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MonthSummaryProcessor {

    private final StatsRepository statsRepository;
    private final RegionService regionService;

    private static final String HEADER_ROW = "CITY,REGION,NO2";
    @Value("${month-summary-generator.directory:month_summary_report}")
    private String directory = "month_summary_report";
    private static final String FILE_NAME_PATTERN = "WORST_CITIES_NO2_%s.csv";
    private static final String DATE_FORMAT = "YYYYmm";

    private static String absoluteDirectory;

    /**
     * Initializes the directory for report generation.
     */
    @PostConstruct
    public void init() {
        absoluteDirectory = generateDirectory();
    }

    /**
     * Generates monthly report with worst cities based on NO2 levels.
     * Job runs on the first day of the month. It analyzes the last month's data and generates a report.
     */
    @Scheduled(cron = "0 0 2 1 * *")
    public void produceMonthSummaryReport() {
        produceMonthSummaryReport(null);
    }

    /**
     * Generates monthly report with worst cities based on NO2 levels.
     *
     * @param dayOfTheMonth the day of the month (optional parameter, in case when report should be triggered for other day)
     */
    public void produceMonthSummaryReport(Instant dayOfTheMonth) {
        LocalDateTime dayInTheAnalyzedMonth = dayOfTheMonth == null
                ? LocalDateTime.now().minus(Duration.ofDays(1))
                : LocalDateTime.from(dayOfTheMonth);
        Instant firstDayOfTheMonth = LocalDateTime.of(dayInTheAnalyzedMonth.getYear(), dayInTheAnalyzedMonth.getMonth(), 1, 0, 0)
                .toInstant(ZoneOffset.UTC);
        List<Stats> lastMonthTop10NoUsage = statsRepository.getLastMonthTop10NOUsage(firstDayOfTheMonth);
        List<ReportRow> reportRows = lastMonthTop10NoUsage.stream()
                .map(stats -> Map.entry(stats.getCityId(), stats.getAverage()))
                .map(entry -> {
                    RegionDto region = regionService.getRegion(entry.getKey());
                    return new ReportRow(region.getCity(), region.getRegion(), entry.getValue());
                })
                .toList();
        String fileName = String.format(FILE_NAME_PATTERN, DateTimeFormatter.ofPattern(DATE_FORMAT).format(Instant.now()));
        generateReportFile(absoluteDirectory, fileName, reportRows);
    }

    private String generateDirectory() {
        File fileDirectory = new File(directory);

        fileDirectory.mkdirs();

        // Verify the directory exists after creation attempt
        if (!fileDirectory.exists() || !fileDirectory.isDirectory()) {
            log.error("Failed to create the directory: {}", fileDirectory.getAbsolutePath());
            throw new StatsMonthlyReportGenerationException();
        }
        return fileDirectory.getAbsolutePath();
    }

    private void generateReportFile(String fileDirectory, String fileName, List<ReportRow> reportRows) {
        String fileFullDirectory = fileDirectory + File.separator + fileName;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileFullDirectory))) {
            bw.write(HEADER_ROW);
            bw.newLine();
            reportRows.forEach(row -> {
                try {
                    bw.write(row.toRow());
                    bw.newLine();
                } catch (IOException e) {
                    log.error("Failed to write row {} to report file: {}", row, fileFullDirectory, e);
                    throw new StatsMonthlyReportGenerationException();
                }
            });
        } catch (IOException e) {
            log.error("Failed to generate report file: {}", fileFullDirectory, e);
            throw new StatsMonthlyReportGenerationException();
        }
    }

    private record ReportRow(String city, String region, BigDecimal no2) {
        private String toRow() {
            return String.format("%s,%s,%s", city, region, no2);
        }
    }
}
