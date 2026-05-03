package org.example.demo.testing;

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

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<RegionDto> getRegion(@PathVariable UUID cityId) {
        return ResponseEntity.ok(regionService.getRegion(cityId));
    }

    @GetMapping("/month-summary")
    public ResponseEntity<String> isValidRegion() {
        monthSummaryProcessor.produceMonthSummaryReport();
        return ResponseEntity.ok("Month summary report generation triggered");
    }
}
