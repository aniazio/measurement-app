package org.example.measurementapp.service.impl;

import org.example.measurementapp.model.RegionDto;
import org.example.measurementapp.service.RegionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mock implementation of the RegionService interface.
 */
@Component
@Profile({"local", "testing"})
@Qualifier("mock")
public class MockRegionService implements RegionService {

    @Override
    public boolean isValidRegionForTheCity(UUID regionId, UUID cityId) {
        return regionId.equals(cityId);
    }

    @Override
    public RegionDto getRegion(UUID cityId) {
        return RegionDto.builder()
                .city("City " + cityId)
                .country("Country " + cityId)
                .region("Region " + cityId)
                .regionId(cityId)
                .build();
    }
}
