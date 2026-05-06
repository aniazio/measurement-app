package org.example.measurementapp.converter;

import org.example.measurementapp.entity.Measurement;
import org.example.measurementapp.model.MeasurementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Converts between Measurement and MeasurementDto.
 */
@Mapper(componentModel = "spring")
public interface MeasurementConverter {

    /**
     * Converts MeasurementDto to Measurement.
     *
     * @param measurementDto input dto
     * @return converted entity
     */
    @Mapping(source = "timestamp", target = "measurementTimestamp")
    Measurement convertToMeasurement(MeasurementDto measurementDto);

    /**
     * Converts Measurement to MeasurementDto.
     *
     * @param measurement input entity
     * @return converted dto
     */
    @Mapping(source = "measurementTimestamp", target = "timestamp")
    MeasurementDto convertToMeasurementDto(Measurement measurement);
}
