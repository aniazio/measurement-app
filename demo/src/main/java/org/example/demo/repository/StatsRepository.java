package org.example.demo.repository;

import org.example.demo.entity.Measurement;
import org.example.demo.entity.MeasurementId;
import org.example.demo.model.FullStats3h;
import org.example.demo.model.Stats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface StatsRepository extends Repository<Measurement, MeasurementId> {

    @Query("""
            SELECT new org.example.demo.model.FullStats3h(avg(m.no2), max(m.no2), min(m.no2), avg(m.co), max(m.co), min(m.co), avg(m.pm10), max(m.pm10), min(m.pm10))
                        FROM Measurement m
                        WHERE m.cityId = :cityId
                        AND m.measurementTimestamp >= (current_timestamp - 3 hour)
            """)
    FullStats3h get3hStats(UUID cityId);

    @Query("""
            SELECT new org.example.demo.model.Stats(m.cityId, avg(m.no2))
            FROM Measurement m
            WHERE m.measurementTimestamp >= date_trunc('month', :firstDayOfLastMonth)
            AND m.measurementTimestamp < date_trunc('month', :firstDayOfLastMonth + 1 month)
            AND exists(select 1 from Measurement inm
                        WHERE inm.cityId = m.cityId
                                    AND inm.measurementTimestamp >= date_trunc('day', :firstDayOfLastMonth)
                                    AND inm.measurementTimestamp < date_trunc('day', :firstDayOfLastMonth + 1 day))
            AND exists(select 1 from Measurement inm
                        WHERE inm.cityId = m.cityId
                                    AND inm.measurementTimestamp >= date_trunc('month', (:firstDayOfLastMonth + 1 month - 1 day))
                                    AND inm.measurementTimestamp < date_trunc('month', :firstDayOfLastMonth + 1 month))
            GROUP BY m.cityId
            ORDER BY avg(m.no2) DESC
            LIMIT 10
            """)
    List<Stats> getLastMonthTop10NOUsage(Instant firstDayOfLastMonth);
}
