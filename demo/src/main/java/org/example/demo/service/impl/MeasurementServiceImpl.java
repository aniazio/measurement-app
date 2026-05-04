package org.example.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.demo.converter.MeasurementConverter;
import org.example.demo.entity.Measurement;
import org.example.demo.exception.InvalidRegionException;
import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;
import org.example.demo.repository.MeasurementRepository;
import org.example.demo.repository.StatsRepository;
import org.example.demo.service.MeasurementService;
import org.example.demo.service.RegionService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Main implementation of the MeasurementService interface.
 */
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final StatsRepository statsRepository;
    private final RegionService regionService;
    private final MeasurementConverter measurementConverter;

    @Override
    @Transactional
    public MeasurementDto save(MeasurementDto measurement) {
        Measurement measurementEntity = measurementConverter.convertToMeasurement(measurement);
        if (measurementRepository.existsById(measurementEntity.getId())) {
            throw new DuplicateKeyException(String.format("Measurement of id %s is already in db", measurementEntity.getId()));
        }
        return measurementConverter.convertToMeasurementDto(measurementRepository.save(measurementEntity));
    }

    @Override
    public FullStats3h get3hStats(UUID regionId, UUID cityId) {
        if (!regionService.isValidRegion(regionId, cityId)) {
            throw new InvalidRegionException(regionId, cityId);
        }
        return statsRepository.get3hStats(cityId);
    }
}
