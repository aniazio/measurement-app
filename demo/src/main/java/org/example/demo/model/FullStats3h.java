package org.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
public class FullStats3h {

    @JsonProperty("avgNO2Last3Hour")
    private BigDecimal avgNo2;
    @JsonProperty("maxNO2Last3Hour")
    private BigDecimal maxNo2;
    @JsonProperty("minNO2Last3Hour")
    private BigDecimal minNo2;
    @JsonProperty("avgCOLast3Hour")
    private BigDecimal avgCo;
    @JsonProperty("maxCOLast3Hour")
    private BigDecimal maxCo;
    @JsonProperty("minCOLast3Hour")
    private BigDecimal minCo;
    @JsonProperty("avgPM10Last3Hour")
    private BigDecimal avgPm10;
    @JsonProperty("maxPM10Last3Hour")
    private BigDecimal maxPm10;
    @JsonProperty("minPM10Last3Hour")
    private BigDecimal minPm10;

    public FullStats3h(Double avgNo2, BigDecimal maxNo2, BigDecimal minNo2,
                       Double avgCo, BigDecimal maxCo, BigDecimal minCo,
                       Double avgPm10, BigDecimal maxPm10, BigDecimal minPm10) {
        this.avgNo2 = BigDecimal.valueOf(avgNo2).setScale(2, RoundingMode.HALF_UP);
        this.maxNo2 = maxNo2;
        this.minNo2 = minNo2;
        this.avgCo = BigDecimal.valueOf(avgCo).setScale(2, RoundingMode.HALF_UP);
        this.maxCo = maxCo;
        this.minCo = minCo;
        this.avgPm10 = BigDecimal.valueOf(avgPm10).setScale(2, RoundingMode.HALF_UP);
        this.maxPm10 = maxPm10;
        this.minPm10 = minPm10;
    }
}
