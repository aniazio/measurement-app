package org.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Simple statistics for a city.
 */
@Data
@Builder
@AllArgsConstructor
public class Stats {

    private UUID cityId;
    private BigDecimal average;

    public Stats(UUID cityId, Double average) {
        this.cityId = cityId;
        this.average = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);
    }
}
