package org.example.demo.testing;

import lombok.RequiredArgsConstructor;
import org.example.demo.job.MonthSummaryProcessor;
import org.example.demo.model.RegionDto;
import org.example.demo.service.impl.MockRegionService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Profile("mock")
@RequiredArgsConstructor
public class TestingController {

    private final MockRegionService mockRegionService;
    private final MonthSummaryProcessor monthSummaryProcessor;

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<RegionDto> getRegion(@PathVariable UUID cityId) {
        return ResponseEntity.ok(mockRegionService.getRegion(cityId));
    }

    @GetMapping("/month-summary")
    public ResponseEntity<String> isValidRegion() {
        monthSummaryProcessor.produceMonthSummaryReport();
        return ResponseEntity.ok("Month summary report generation triggered");
    }
}
