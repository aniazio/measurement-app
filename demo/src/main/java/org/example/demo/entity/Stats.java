package org.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Data
public class Stats implements Serializable {

    private UUID cityId;
    private long count = 0;
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.valueOf(Double.MAX_VALUE);
    private BigDecimal max = BigDecimal.valueOf(Double.MIN_VALUE);

    public BigDecimal getAverage() {
        return count == 0
                ? BigDecimal.ZERO
                : sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    public void addOneStat(BigDecimal value) {
        count++;
        sum = sum.add(value);
        min = min.min(value);
        max = max.max(value);
    }

    public void removeOneStat(BigDecimal value) {
        count--;
        sum = sum.subtract(value);
        min = min.min(value);
        max = max.max(value);
    }
}
