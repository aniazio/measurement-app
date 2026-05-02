package org.example.demo.entity;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEASUREMENT")
@IdClass(MeasurementId.class)
public class Measurement {

    @Id
    private UUID sensorId;
    @Id
    private UUID cityId;
    private BigDecimal pm10;
    private BigDecimal co;
    private BigDecimal no2;
    @Id
    private Instant timestamp;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;
        return sensorId.equals(that.sensorId) && cityId.equals(that.cityId) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = sensorId.hashCode();
        result = 31 * result + cityId.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }
}
