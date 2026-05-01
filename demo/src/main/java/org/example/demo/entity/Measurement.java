package org.example.demo.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Measurement {

    private UUID sensorId;
    private UUID cityId;
    private BigDecimal pm10;
    private BigDecimal co;
    private BigDecimal no2;
    private Instant timestamp;
}
