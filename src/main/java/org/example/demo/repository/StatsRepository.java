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

/**
 * Repository for statistics.
 */
public interface StatsRepository extends Repository<Measurement, MeasurementId> {

    /**
     * Retrieves the last 3 hours of statistics for a city.
     *
     * @param cityId the ID of the city
     * @return the last 3 hours of statistics for the city
     */
    @Query("""
            SELECT new org.example.demo.model.FullStats3h(avg(m.no2), max(m.no2), min(m.no2), avg(m.co), max(m.co), min(m.co), avg(m.pm10), max(m.pm10), min(m.pm10))
                        FROM Measurement m
                        WHERE m.cityId = :cityId
                        AND m.measurementTimestamp >= (current_timestamp - 3 hour)
            """)
    FullStats3h get3hStats(UUID cityId);

    /**
     * Retrieves statistics of 10 worst cities from the month.
     *
     * @param firstDayOfLastMonth the first day of the last month
     * @return statistics of 10 worst cities from the month
     */
    @Query("""
            SELECT new org.example.demo.model.Stats(m.cityId, avg(m.no2))
            FROM Measurement m
            WHERE m.measurementTimestamp >= date_trunc('month', cast(:firstDayOfLastMonth as timestamp))
            AND m.measurementTimestamp < date_trunc('month', cast(:firstDayOfLastMonth as timestamp) + 1 month)
            AND exists(select 1 from Measurement inm
                        WHERE inm.cityId = m.cityId
                                    AND inm.measurementTimestamp >= date_trunc('day', cast(:firstDayOfLastMonth as timestamp))
                                    AND inm.measurementTimestamp < date_trunc('day', cast(:firstDayOfLastMonth as timestamp) + 1 day))
            AND exists(select 1 from Measurement inm
                        WHERE inm.cityId = m.cityId
                                    AND inm.measurementTimestamp >= date_trunc('day', (cast(:firstDayOfLastMonth as timestamp) + 1 month - 1 day))
                                    AND inm.measurementTimestamp < date_trunc('day', cast(:firstDayOfLastMonth as timestamp) + 1 month))
            GROUP BY m.cityId
            ORDER BY avg(m.no2) DESC
            LIMIT 10
            """)
    List<Stats> getLastMonthTop10NOUsage(Instant firstDayOfLastMonth);
}
