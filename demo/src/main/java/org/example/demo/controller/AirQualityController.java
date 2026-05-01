package org.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;
import org.example.demo.service.MeasurementService;
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

    @PostMapping("/measurement")
    ResponseEntity<Void> createNewMeasurementRecord(@RequestBody MeasurementDto measurement) {
        measurementService.save(measurement);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/region/{regionId}/city/{cityId}/stats/3H")
    ResponseEntity<FullStats3h> getStats3h(@PathVariable UUID regionId, @PathVariable UUID cityId) {
        return ResponseEntity.ok(measurementService.get3hStats(regionId, cityId));
    }
}
