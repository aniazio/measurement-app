package org.example.measurementapp.repository;

import org.example.measurementapp.model.FullStats3h;
import org.example.measurementapp.model.Stats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql("/test-data.sql")
class StatsRepositoryIT {

    @Autowired
    private StatsRepository statsRepository;

    @Test
    @DisplayName("get3hStats when testData in db expects correctly calculated stats")
    void testGet3hStatsWhenTestDataExpectsCorrectlyCalculatedStats() {
        // given
        FullStats3h expectedResult = FullStats3h.builder()
                .avgPm10(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP))
                .maxPm10(BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_UP))
                .minPm10(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP))
                .avgNo2(BigDecimal.valueOf(2).setScale(2, RoundingMode.HALF_UP))
                .maxNo2(BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP))
                .minNo2(BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP))
                .avgCo(BigDecimal.valueOf(22).setScale(2, RoundingMode.HALF_UP))
                .maxCo(BigDecimal.valueOf(33).setScale(2, RoundingMode.HALF_UP))
                .minCo(BigDecimal.valueOf(11).setScale(2, RoundingMode.HALF_UP))
                .build();
        // when
        FullStats3h result = statsRepository.get3hStats(UUID.fromString("00000000-0000-0000-0000-000000000014"));
        // then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("getLastMonthTop10NOUsage when testData in db expects correctly calculated stats")
    void testGetLastMonthTop10NOUsageWhenTestDataInDbExpectCorrectlyCalculatedStats() {
        // given
        List<Stats> expectedResult = IntStream.range(1, 11)
                .map(i -> 11 - i)
                .mapToObj(i -> Stats.builder()
                        .cityId(new UUID(0, i))
                        .average(BigDecimal.valueOf((31 + ((double) i) / 10)/3).setScale(2, RoundingMode.HALF_UP))
                        .build())
                .toList();
        //when
        List<Stats> result = statsRepository.getLastMonthTop10NOUsage(LocalDateTime.of(2024, 12, 1, 12, 0).toInstant(ZoneOffset.UTC));
        //then
        assertEquals(expectedResult, result);
    }
}
