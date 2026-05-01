package org.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class MeasurementDto implements Serializable {

    private UUID sensorId;
    private UUID cityId;
    @JsonProperty("PM10")
    private BigDecimal pm10;
    @JsonProperty("CO")
    private BigDecimal co;
    @JsonProperty("NO2")
    private BigDecimal no2;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Instant timestamp;
}
