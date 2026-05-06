package org.example.measurementapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.measurementapp.converter.MeasurementConverter;
import org.example.measurementapp.entity.Measurement;
import org.example.measurementapp.exception.InvalidRegionException;
import org.example.measurementapp.model.FullStats3h;
import org.example.measurementapp.model.MeasurementDto;
import org.example.measurementapp.repository.MeasurementRepository;
import org.example.measurementapp.repository.StatsRepository;
import org.example.measurementapp.service.MeasurementService;
import org.example.measurementapp.service.RegionService;
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
        if (!regionService.isValidRegionForTheCity(regionId, cityId)) {
            throw new InvalidRegionException(regionId, cityId);
        }
        return statsRepository.get3hStats(cityId);
    }
}
