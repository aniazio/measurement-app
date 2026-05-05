package org.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * MeasurementId class, for storing primary key of {@link Measurement}.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementId implements Serializable {

    private UUID sensorId;
    private UUID cityId;
    private Instant measurementTimestamp;
}
