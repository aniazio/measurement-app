package org.example.measurementapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Measurement entity, to store information about measurement in particular city.
 * Measurement timestamp, sensor id and city id should be unique.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEASUREMENT")
@IdClass(MeasurementId.class)
public class Measurement {

    @Id
    @Column(name = "SENSOR_ID", nullable = false)
    private UUID sensorId;
    @Id
    @Column(name = "CITY_ID", nullable = false)
    private UUID cityId;
    private BigDecimal pm10;
    private BigDecimal co;
    private BigDecimal no2;
    @Id
    @Column(name = "MEASUREMENT_TIMESTAMP", nullable = false)
    private Instant measurementTimestamp;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;
        return sensorId.equals(that.sensorId) && cityId.equals(that.cityId) && measurementTimestamp.equals(that.measurementTimestamp);
    }

    @Override
    public int hashCode() {
        int result = sensorId.hashCode();
        result = 31 * result + cityId.hashCode();
        result = 31 * result + measurementTimestamp.hashCode();
        return result;
    }

    public MeasurementId getId() {
        return MeasurementId.builder()
                .sensorId(sensorId)
                .cityId(cityId)
                .measurementTimestamp(measurementTimestamp)
                .build();
    }
}
