package org.example.demo.service;

import org.example.demo.model.RegionDto;

import java.util.UUID;

public interface RegionService {

    boolean isValidRegion(UUID regionId, UUID cityId);

    RegionDto getRegion(UUID cityId);
}
