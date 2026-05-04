package org.example.demo.testing;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.demo.job.MonthSummaryProcessor;
import org.example.demo.model.RegionDto;
import org.example.demo.service.RegionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Profile("test")
public class TestingController {

    private final RegionService regionService;
    private final MonthSummaryProcessor monthSummaryProcessor;

    public TestingController(@Qualifier("mock") RegionService regionService,
                             MonthSummaryProcessor monthSummaryProcessor) {
        this.regionService = regionService;
        this.monthSummaryProcessor = monthSummaryProcessor;
    }

    @Operation(summary = "Get region by city id", description = "Get region by city id - testing implementation, which returns the same region id as city id")
    @ApiResponse(responseCode = "200", description = "Region found")
    @GetMapping("/cities/{cityId}")
    public ResponseEntity<RegionDto> getRegion(@PathVariable UUID cityId) {
        return ResponseEntity.ok(regionService.getRegion(cityId));
    }

    @Operation(summary = "Generate month summary report", description = "Generate month summary report - testing implementation to manually trigger month summary report generation")
    @ApiResponse(responseCode = "200", description = "Month summary report generation triggered")
    @GetMapping("/month-summary")
    public ResponseEntity<String> isValidRegion() {
        monthSummaryProcessor.produceMonthSummaryReport();
        return ResponseEntity.ok("Month summary report generation triggered");
    }
}
