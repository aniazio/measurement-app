package org.example.demo.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.example.demo.entity.Measurement;
import org.example.demo.model.MeasurementDto;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsConverter {

    public static Measurement convertToMeasurement(MeasurementDto measurementDto) {
        return Measurement.builder()
                .co(measurementDto.getCo())
                .no2(measurementDto.getNo2())
                .pm10(measurementDto.getPm10())
                .timestamp(measurementDto.getTimestamp())
                .cityId(measurementDto.getCityId())
                .sensorId(measurementDto.getSensorId())
                .build();
    }
}
