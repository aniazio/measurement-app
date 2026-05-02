package org.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Data
@Builder
public class Stats {

    private UUID cityId;
    private BigDecimal average;

    public Stats(UUID cityId, Double average) {
        this.cityId = cityId;
        this.average = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);
    }
}
