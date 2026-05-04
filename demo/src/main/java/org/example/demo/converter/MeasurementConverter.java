package org.example.demo.converter;

import org.example.demo.entity.Measurement;
import org.example.demo.model.MeasurementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeasurementConverter {

    @Mapping(source = "timestamp", target = "measurementTimestamp")
    Measurement convertToMeasurement(MeasurementDto measurementDto);

    @Mapping(source = "measurementTimestamp", target = "timestamp")
    MeasurementDto convertToMeasurementDto(Measurement measurement);
}
