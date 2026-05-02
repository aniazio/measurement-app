package org.example.demo.job;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.exception.StatsMonthlyReportGenerationException;
import org.example.demo.model.RegionDto;
import org.example.demo.model.Stats;
import org.example.demo.repository.StatsRepository;
import org.example.demo.service.RegionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MonthSummaryProcessor {

    private final StatsRepository statsRepository;
    private final RegionService regionService;

    private static final String HEADER_ROW = "CITY,REGION,NO2";
    private static final String DIRECTORY = "month_summary_report";
    private static final String FILE_NAME_PATTERN = "month_summary_report_%s.csv";

    private static String absoluteDirectory;

    @PostConstruct
    public void init() {
        absoluteDirectory = generateDirectory();
    }

    @Scheduled(cron = "0 0 2 L * *")
    public void produceMonthSummaryReport() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1); //TODO clarify
        Instant firstDayOfLastMonth = LocalDateTime.of(lastMonth.getYear(), lastMonth.getMonth(), 1, 0, 0)
                .toInstant(ZoneOffset.UTC);
        List<Stats> lastMonthTop10NoUsage = statsRepository.getLastMonthTop10NOUsage(firstDayOfLastMonth);
        List<ReportRow> reportRows = lastMonthTop10NoUsage.stream()
                .map(stats -> Map.entry(stats.getCityId(), stats.getAverage()))
                .map(entry -> {
                    RegionDto region = regionService.getRegion(entry.getKey());
                    return new ReportRow(region.getCity(), region.getRegion(), entry.getValue());
                })
                .toList();
        String fileName = String.format(FILE_NAME_PATTERN, DateTimeFormatter.ofPattern("yyyy-MM").format(Instant.now()));
        generateReportFile(absoluteDirectory, fileName, reportRows);
    }

    private String generateDirectory() {
        File directory = new File(MonthSummaryProcessor.DIRECTORY);

        directory.mkdirs();

        // Verify the directory exists after creation attempt
        if (!directory.exists() || !directory.isDirectory()) {
            log.error("Failed to create the directory: {}", directory.getAbsolutePath());
            throw new StatsMonthlyReportGenerationException();
        }
        return directory.getAbsolutePath();
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
