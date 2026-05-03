package org.example.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo.converter.MeasurementConverter;
import org.example.demo.exception.InvalidRegionException;
import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;
import org.example.demo.repository.MeasurementRepository;
import org.example.demo.repository.StatsRepository;
import org.example.demo.service.MeasurementService;
import org.example.demo.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final StatsRepository statsRepository;
    private final RegionService regionService;

    @Override
    public MeasurementDto save(MeasurementDto measurement) {
        measurementRepository.save(MeasurementConverter.INSTANCE.convertToMeasurement(measurement));
        return measurement;
    }

    @Override
    public FullStats3h get3hStats(UUID regionId, UUID cityId) {
        if (!regionService.isValidRegion(regionId, cityId)) {
            throw new InvalidRegionException(regionId, cityId);
        }
        return statsRepository.get3hStats(cityId);
    }
}
