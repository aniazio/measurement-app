package org.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

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

}
