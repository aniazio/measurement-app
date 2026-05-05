package org.example.demo.service.impl;

import org.example.demo.converter.MeasurementConverter;
import org.example.demo.entity.Measurement;
import org.example.demo.exception.InvalidRegionException;
import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;
import org.example.demo.repository.MeasurementRepository;
import org.example.demo.repository.StatsRepository;
import org.example.demo.service.RegionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceImplTest {

    @Mock
    private MeasurementRepository measurementRepository;
    @Mock
    private StatsRepository statsRepository;
    @Mock
    private RegionService regionService;
    @Mock
    private MeasurementConverter measurementConverter;
    @InjectMocks
    private MeasurementServiceImpl measurementService;


    @Test
    void testSaveWhenEntityExistsInDbExpectsException() {
        //given
        MeasurementDto measurementDto = createMeasurementDto();
        Measurement measurementAfterConversion = createMeasurement();
        given(measurementConverter.convertToMeasurement(measurementDto))
                .willReturn(measurementAfterConversion);
        given(measurementRepository.existsById(measurementAfterConversion.getId()))
                .willReturn(true);
        //when
        //then
        assertThrows(DuplicateKeyException.class, () -> measurementService.save(measurementDto));
        then(measurementConverter).should().convertToMeasurement(measurementDto);
        then(measurementRepository).should().existsById(measurementAfterConversion.getId());
        then(measurementRepository).shouldHaveNoMoreInteractions();
        then(statsRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testSaveWhenNewEntityExpectsSuccess() {
        //given
        MeasurementDto measurementDto = createMeasurementDto();
        Measurement measurementAfterConversion = createMeasurement();
        Measurement savedMeasurement = createMeasurement();
        MeasurementDto savedMeasurementDto = createMeasurementDto();
        given(measurementConverter.convertToMeasurement(measurementDto))
                .willReturn(measurementAfterConversion);
        given(measurementRepository.existsById(measurementAfterConversion.getId()))
                .willReturn(false);
        given(measurementRepository.save(measurementAfterConversion))
                .willReturn(savedMeasurement);
        given(measurementConverter.convertToMeasurementDto(savedMeasurement))
                .willReturn(savedMeasurementDto);

        //when
        MeasurementDto result = measurementService.save(measurementDto);
        //then
        assertEquals(savedMeasurementDto, result);
        then(measurementConverter).should().convertToMeasurement(measurementDto);
        then(measurementRepository).should().existsById(measurementAfterConversion.getId());
        then(measurementRepository).should().save(measurementAfterConversion);
        then(measurementConverter).should().convertToMeasurementDto(savedMeasurement);
    }

    @Test
    void testGet3hStatsWhenInvalidRegionExpectsException() {
        //given
        UUID cityId = UUID.randomUUID();
        UUID invalidRegionId = UUID.randomUUID();
        given(regionService.isValidRegionForTheCity(invalidRegionId, cityId)).willReturn(false);
        //when
        //then
        assertThrows(InvalidRegionException.class, () -> measurementService.get3hStats(invalidRegionId, cityId));
    }

    @Test
    void testGet3hStatsWhenValidRegionExpectsSuccess() {
        //given
        UUID cityId = UUID.randomUUID();
        UUID regionId = UUID.randomUUID();
        FullStats3h stats = FullStats3h.builder()
                .avgCo(BigDecimal.ONE)
                .avgNo2(BigDecimal.ONE)
                .avgPm10(BigDecimal.ONE)
                .build();
        given(regionService.isValidRegionForTheCity(regionId, cityId)).willReturn(true);
        given(statsRepository.get3hStats(cityId)).willReturn(stats);
        //when
        FullStats3h result = measurementService.get3hStats(regionId, cityId);
        //then
        assertEquals(stats, result);
        then(regionService).should().isValidRegionForTheCity(regionId, cityId);
        then(statsRepository).should().get3hStats(cityId);
        then(regionService).shouldHaveNoMoreInteractions();
        then(statsRepository).shouldHaveNoMoreInteractions();
    }

    private MeasurementDto createMeasurementDto() {
        return MeasurementDto.builder()
                .cityId(UUID.randomUUID())
                .timestamp(Instant.now())
                .sensorId(UUID.randomUUID())
                .pm10(BigDecimal.ONE)
                .no2(BigDecimal.ONE)
                .co(BigDecimal.ONE)
                .build();
    }

    private Measurement createMeasurement() {
        return Measurement.builder()
                .cityId(UUID.randomUUID())
                .measurementTimestamp(Instant.now())
                .sensorId(UUID.randomUUID())
                .pm10(BigDecimal.ONE)
                .no2(BigDecimal.ONE)
                .co(BigDecimal.ONE)
                .build();
    }
}
