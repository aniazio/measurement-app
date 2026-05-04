package org.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;
import org.example.demo.service.MeasurementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api")
@RequiredArgsConstructor
public class AirQualityController {

    private final MeasurementService measurementService;

    @Operation(summary = "Create new measurement record", description = "Create new measurement record")
    @ApiResponse(responseCode = "201", description = "Successfully created")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @PostMapping("/measurement")
    ResponseEntity<MeasurementDto> createNewMeasurementRecord(@RequestBody @Valid MeasurementDto measurement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(measurementService.save(measurement));
    }

    @Operation(summary = "Get 3h stats", description = "Get stats for particular city from last 3 hours")
    @ApiResponse(responseCode = "201", description = "Stats calculated")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @GetMapping("/region/{regionId}/city/{cityId}/stats/3H")
    ResponseEntity<FullStats3h> getStats3h(@PathVariable UUID regionId, @PathVariable UUID cityId) {
        return ResponseEntity.ok(measurementService.get3hStats(regionId, cityId));
    }
}
