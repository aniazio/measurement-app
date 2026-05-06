package org.example.measurementapp.converter;

import org.example.measurementapp.entity.Measurement;
import org.example.measurementapp.model.MeasurementDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeasurementConverterTest {

    private MeasurementConverter measurementConverter = Mappers.getMapper(MeasurementConverter.class);

    @Test
    @DisplayName("convertToMeasurement when proper input expects measurement entity constructed")
    void testConvertToMeasurementWhenProperInputExpectMeasurement() {
        //given
        MeasurementDto input = MeasurementDto.builder()
                .cityId(UUID.randomUUID())
                .co(BigDecimal.ONE)
                .no2(BigDecimal.TEN)
                .pm10(BigDecimal.ZERO)
                .sensorId(UUID.randomUUID())
                .timestamp(Instant.now())
                .build();
        Measurement expectedResult = Measurement.builder()
                .cityId(input.getCityId())
                .co(input.getCo())
                .no2(input.getNo2())
                .pm10(input.getPm10())
                .sensorId(input.getSensorId())
                .measurementTimestamp(input.getTimestamp())
                .build();
        //when
        Measurement output = measurementConverter.convertToMeasurement(input);
        //then
        assertEquals(expectedResult, output);
        assertEquals(expectedResult.getCityId(), output.getCityId());
        assertEquals(expectedResult.getCo(), output.getCo());
        assertEquals(expectedResult.getNo2(), output.getNo2());
        assertEquals(expectedResult.getPm10(), output.getPm10());
        assertEquals(expectedResult.getSensorId(), output.getSensorId());
        assertEquals(expectedResult.getMeasurementTimestamp(), output.getMeasurementTimestamp());
    }

    @Test
    @DisplayName("convertToMeasurementDto when proper input expects measurement dto constructed")
    void testConvertToMeasurementDtoWhenProperInputExpectMeasurementDto() {
        //given
        Measurement input = Measurement.builder()
                .cityId(UUID.randomUUID())
                .co(BigDecimal.ONE)
                .no2(BigDecimal.TEN)
                .pm10(BigDecimal.ZERO)
                .sensorId(UUID.randomUUID())
                .measurementTimestamp(Instant.now())
                .build();
        MeasurementDto expectedResult = MeasurementDto.builder()
                .cityId(input.getCityId())
                .co(input.getCo())
                .no2(input.getNo2())
                .pm10(input.getPm10())
                .sensorId(input.getSensorId())
                .timestamp(input.getMeasurementTimestamp())
                .build();
        //when
        MeasurementDto output = measurementConverter.convertToMeasurementDto(input);
        //then
        assertEquals(expectedResult, output);
        assertEquals(expectedResult.getCityId(), output.getCityId());
        assertEquals(expectedResult.getCo(), output.getCo());
        assertEquals(expectedResult.getNo2(), output.getNo2());
        assertEquals(expectedResult.getPm10(), output.getPm10());
        assertEquals(expectedResult.getSensorId(), output.getSensorId());
        assertEquals(expectedResult.getTimestamp(), output.getTimestamp());
    }
}
