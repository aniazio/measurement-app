package org.example.demo.service.impl;

import org.example.demo.model.RegionDto;
import org.example.demo.service.RegionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mock implementation of the RegionService interface.
 */
@Component
@Profile({"local", "test"})
@Qualifier("mock")
public class MockRegionService implements RegionService {

    @Override
    public boolean isValidRegionForTheCity(UUID regionId, UUID cityId) {
        return true;
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
