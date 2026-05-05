package org.example.demo.service.impl;

import org.example.demo.model.RegionDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MockRegionServiceTest {

    private final MockRegionService mockRegionService = new MockRegionService();

    @Test
    void testIsValidRegionWhenCityIdEqualsRegionForTheCityIdExpectsTrue() {
        UUID regionId = UUID.randomUUID();

        assertTrue(mockRegionService.isValidRegionForTheCity(regionId, regionId));
    }

    @Test
    void testIsValidRegionWhenCityIdDoesNotEqualRegionForTheCityIdExpectsFalse() {
        UUID regionId = UUID.randomUUID();
        UUID cityId = UUID.randomUUID();

        assertFalse(mockRegionService.isValidRegionForTheCity(regionId, cityId));
    }

    @Test
    void testGetRegionWhenTriggeredExpectsValidRegionResponse() {
        //given
        UUID cityId = UUID.randomUUID();
        //when
        RegionDto region = mockRegionService.getRegion(cityId);
        //then
        assertNotNull(region);
        assertEquals(cityId, region.getRegionId());
        assertTrue(region.getCity().contains(cityId.toString()));
        assertTrue(region.getRegion().contains(cityId.toString()));

        assertTrue(mockRegionService.isValidRegionForTheCity(region.getRegionId(), cityId));
    }
}
