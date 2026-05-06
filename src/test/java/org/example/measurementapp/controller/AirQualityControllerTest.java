package org.example.measurementapp.controller;

import org.example.measurementapp.model.FullStats3h;
import org.example.measurementapp.model.MeasurementDto;
import org.example.measurementapp.service.MeasurementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AirQualityControllerTest {

    @Mock
    private MeasurementService measurementService;

    @InjectMocks
    private AirQualityController airQualityController;


    @Test
    @DisplayName("createNewMeasurementRecord when proper input expects measurement service invocation")
    void testCreateNewMeasurementRecordWhenProperInputExpectServiceInvocation() {
        // given
        MeasurementDto input = MeasurementDto.builder()
                .cityId(UUID.randomUUID())
                .timestamp(Instant.now())
                .build();
        MeasurementDto output = MeasurementDto.builder()
                .cityId(UUID.randomUUID())
                .timestamp(Instant.now())
                .co(BigDecimal.ONE)
                .build();
        given(measurementService.save(input)).willReturn(output);

        // when
        ResponseEntity<MeasurementDto> response = airQualityController.createNewMeasurementRecord(input);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(output, response.getBody());
    }

    @Test
    @DisplayName("getStats3h when proper input expects measurement service invocation")
    void testGetStats3hWhenProperInputExpectServiceInvocation() {
        //given
        UUID cityId = UUID.randomUUID();
        UUID regionId = UUID.randomUUID();
        FullStats3h output = FullStats3h.builder()
                .avgCo(new BigDecimal(1))
                .avgPm10(new BigDecimal(2))
                .avgNo2(new BigDecimal(3))
                .build();
        given(measurementService.get3hStats(cityId, regionId)).willReturn(output);
        //when
        ResponseEntity<FullStats3h> response = airQualityController.getStats3h(cityId, regionId);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(output, response.getBody());
    }
}
