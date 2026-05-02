package org.example.demo.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class MeasurementId implements Serializable {

    private UUID sensorId;
    private UUID cityId;
    private Instant timestamp;
}
