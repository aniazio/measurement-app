package org.example.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode
public class MeasurementId implements Serializable {

    private UUID sensorId;
    private UUID cityId;
    private Instant measurementTimestamp;
}
