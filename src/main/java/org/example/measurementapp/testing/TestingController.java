package org.example.measurementapp.testing;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.measurementapp.job.MonthSummaryProcessor;
import org.example.measurementapp.model.RegionDto;
import org.example.measurementapp.service.RegionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Controller only for testing purposes.
 */
@RestController
@Profile("testing")
public class TestingController {

    private final RegionService regionService;
    private final MonthSummaryProcessor monthSummaryProcessor;

    public TestingController(@Qualifier("mock") RegionService regionService,
                             MonthSummaryProcessor monthSummaryProcessor) {
        this.regionService = regionService;
        this.monthSummaryProcessor = monthSummaryProcessor;
    }

    /**
     * Gets region for a city. Used as a mock for external region service.
     *
     * @param cityId id of the city
     * @return region dto
     */
    @Operation(summary = "Get region by city id", description = "Get region by city id - testing implementation, which returns the same region id as city id")
    @ApiResponse(responseCode = "200", description = "Region found")
    @GetMapping("/cities/{cityId}")
    public ResponseEntity<RegionDto> getRegion(@PathVariable UUID cityId) {
        return ResponseEntity.ok(regionService.getRegion(cityId));
    }

    /**
     * Generates summary report for the month. Used for manual trigger for month summary report generation, only for testing purposes.
     *
     * @param dayOfTheMonth day of the month for which the report should be generated
     * @return message indicating that month summary report generation was triggered
     */
    @Operation(summary = "Generate summary report for the month", description = "Generate summary report for the month - testing implementation to manually trigger month summary report generation")
    @ApiResponse(responseCode = "200", description = "Month summary report generation triggered")
    @GetMapping("/month-summary")
    public ResponseEntity<String> generateMonthlySummary(@RequestParam(defaultValue = "2024-12-12T08:16:10.303") LocalDateTime dayOfTheMonth) {
        monthSummaryProcessor.produceMonthSummaryReport(dayOfTheMonth);
        return ResponseEntity.ok("Month summary report generation triggered");
    }
}
