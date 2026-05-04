package org.example.demo.service;

import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;

import java.util.UUID;

/**
 * Service interface for handling measurements.
 */
public interface MeasurementService {

    /**
     * Saves measurement.
     *
     * @param measurement measurement to be saved
     * @return saved object
     */
    MeasurementDto save(MeasurementDto measurement);

    /**
     * Get stats for last 3 hours for a city.
     *
     * @param regionId id of the region in which the city is located
     * @param cityId   city id
     * @return stats for last 3 hours for a city
     * @throws org.example.demo.exception.InvalidRegionException if region is invalid
     */
    FullStats3h get3hStats(UUID regionId, UUID cityId);
}
