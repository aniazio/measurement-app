package org.example.measurementapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Measurement data transfer object, for creating measurements.
 */
@Data
@Builder
@AllArgsConstructor
public class MeasurementDto implements Serializable {

    @NotNull
    private UUID sensorId;

    @NotNull
    private UUID cityId;

    @JsonProperty("PM10")
    private BigDecimal pm10;

    @JsonProperty("CO")
    private BigDecimal co;

    @JsonProperty("NO2")
    private BigDecimal no2;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @NotNull
    @PastOrPresent
    private Instant timestamp;
}
