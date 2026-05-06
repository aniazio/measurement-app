package org.example.measurementapp.repository;

import org.example.measurementapp.entity.Measurement;
import org.example.measurementapp.entity.MeasurementId;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for CRUD operations on measurements.
 */
public interface MeasurementRepository extends CrudRepository<Measurement, MeasurementId> {
}
