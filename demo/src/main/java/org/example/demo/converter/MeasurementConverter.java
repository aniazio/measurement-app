package org.example.demo.converter;

import org.example.demo.entity.Measurement;
import org.example.demo.model.MeasurementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MeasurementConverter {

    MeasurementConverter INSTANCE = Mappers.getMapper(MeasurementConverter.class);

    @Mapping(source = "timestamp", target = "measurementTimestamp")
    Measurement convertToMeasurement(MeasurementDto measurementDto);
}
