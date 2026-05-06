package org.example.demo.repository;

import org.example.demo.entity.Measurement;
import org.example.demo.entity.MeasurementId;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for CRUD operations on measurements.
 */
public interface MeasurementRepository extends CrudRepository<Measurement, MeasurementId> {
}
