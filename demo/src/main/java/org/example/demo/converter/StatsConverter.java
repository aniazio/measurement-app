package org.example.demo.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.example.demo.entity.FullStats;
import org.example.demo.entity.Measurement;
import org.example.demo.model.FullStats3h;
import org.example.demo.model.MeasurementDto;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsConverter {

    public static FullStats3h convertToFullStats3h(FullStats fullStats) {
        return FullStats3h.builder()
                .avgNo2(fullStats.getNo2Stats() != null ? fullStats.getNo2Stats().getAverage() : null)
                .minNo2(fullStats.getNo2Stats() != null ? fullStats.getNo2Stats().getMin() : null)
                .maxNo2(fullStats.getNo2Stats() != null ? fullStats.getNo2Stats().getMax() : null)
                .avgCo(fullStats.getCoStats() != null ? fullStats.getCoStats().getAverage() : null)
                .minCo(fullStats.getCoStats() != null ? fullStats.getCoStats().getMin() : null)
                .maxCo(fullStats.getCoStats() != null ? fullStats.getCoStats().getMax() : null)
                .avgPm10(fullStats.getPm10Stats() != null ? fullStats.getPm10Stats().getAverage() : null)
                .minPm10(fullStats.getPm10Stats() != null ? fullStats.getPm10Stats().getMin() : null)
                .maxPm10(fullStats.getPm10Stats() != null ? fullStats.getPm10Stats().getMax() : null)
                .build();
    }

    public static Measurement convertToMeasurement(MeasurementDto measurementDto) {
        return Measurement.builder()
                .co(measurementDto.getCo())
                .no2(measurementDto.getNo2())
                .pm10(measurementDto.getPm10())
                .timestamp(measurementDto.getTimestamp())
                .cityId(measurementDto.getCityId())
                .sensorId(measurementDto.getSensorId())
                .build();
    }
}
