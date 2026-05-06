package org.example.measurementapp.service;

import org.example.measurementapp.model.RegionDto;

import java.util.UUID;

/**
 * Service interface for handling regions.
 */
public interface RegionService {

    /**
     * Checks, if the city is assigned to the region.
     *
     * @param regionId id of region
     * @param cityId   id of city
     * @return true, if the city is assigned to the region (valid region for that city)
     */
    boolean isValidRegionForTheCity(UUID regionId, UUID cityId);

    /**
     * Get region for the city.
     *
     * @param cityId id of city
     * @return region for the city
     */
    RegionDto getRegion(UUID cityId);
}
