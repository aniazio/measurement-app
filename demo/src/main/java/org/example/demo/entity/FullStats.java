package org.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FullStats implements Serializable {

    private Stats no2Stats;
    private Stats coStats;
    private Stats pm10Stats;

    public void addStats(BigDecimal no2, BigDecimal co, BigDecimal pm10) {
        no2Stats.addOneStat(no2);
        coStats.addOneStat(co);
        pm10Stats.addOneStat(pm10);
    }

    public void removeStats(BigDecimal no2, BigDecimal co, BigDecimal pm10) {
        no2Stats.removeOneStat(no2);
        coStats.removeOneStat(co);
        pm10Stats.removeOneStat(pm10);
    }
}
