package org.example.measurementapp.controller;

import org.example.measurementapp.exception.handler.GlobalExceptionHandler;
import org.example.measurementapp.model.FullStats3h;
import org.example.measurementapp.model.MeasurementDto;
import org.example.measurementapp.service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AirQualityControllerIT {

    @Mock
    private MeasurementService measurementService;
    @InjectMocks
    private AirQualityController airQualityController;
    private MockMvc mockMvc;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(airQualityController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @DisplayName("createNewMeasurementRecord when proper input expects 201")
    void testCreateNewMeasurementRecordWhenProperInputExpect201() throws Exception {
        MeasurementDto input = MeasurementDto.builder()
                .cityId(UUID.randomUUID())
                .timestamp(Instant.now().minus(2, ChronoUnit.DAYS))
                .sensorId(UUID.randomUUID())
                .build();
        MeasurementDto output = MeasurementDto.builder()
                .cityId(UUID.randomUUID())
                .timestamp(Instant.now())
                .co(BigDecimal.ONE)
                .build();
        given(measurementService.save(input)).willReturn(output);

        mockMvc.perform(post("/api/measurement")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(OBJECT_MAPPER.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(output)));
    }

    @Test
    @DisplayName("createNewMeasurementRecord when input doesn't pas validation expects 400")
    void testCreateNewMeasurementRecordWhenNotValidInputExpect400() throws Exception {
        MeasurementDto input = MeasurementDto.builder() //empty input
                .build();

        mockMvc.perform(post("/api/measurement")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(OBJECT_MAPPER.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getStats3h when proper input expects 200")
    void testGetStats3hWhenProperInputExpect200() throws Exception {
        UUID cityId = UUID.randomUUID();
        UUID regionId = UUID.randomUUID();
        FullStats3h output = FullStats3h.builder()
                .avgCo(new BigDecimal(1))
                .avgPm10(new BigDecimal(2))
                .avgNo2(new BigDecimal(3))
                .build();
        given(measurementService.get3hStats(regionId, cityId)).willReturn(output);

        mockMvc.perform(get(String.format("/api/region/%s/city/%s/stats/3H", regionId, cityId)))
                .andExpect(status().isOk())
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(output)));
    }
}
