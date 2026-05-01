package org.example.demo.service;

import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;

import java.util.UUID;

public interface MeasurementService {

    MeasurementDto save(MeasurementDto measurement);

    FullStats3h get3hStats(UUID regionId, UUID cityId);
}
