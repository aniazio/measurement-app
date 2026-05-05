package org.example.demo.repository;

import org.example.demo.model.FullStats3h;
import org.example.demo.model.Stats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        StatsRepository.class
})
@Testcontainers
class StatsRepositoryIT {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:16.2")
            .asCompatibleSubstituteFor("postgres"))
            .withInitScript("test-data.sql");

    @Autowired
    private StatsRepository statsRepository;

    @Test
    @DisplayName("get3hStats when testData in db expects correctly calculated stats")
    void testGet3hStatsWhenTestDataExpectsCorrectlyCalculatedStats() {
        // given
        FullStats3h expectedResult = FullStats3h.builder()
                .avgPm10(BigDecimal.valueOf(20))
                .maxPm10(BigDecimal.valueOf(30))
                .minPm10(BigDecimal.valueOf(10))
                .avgNo2(BigDecimal.valueOf(2))
                .maxNo2(BigDecimal.valueOf(3))
                .minNo2(BigDecimal.valueOf(1))
                .avgCo(BigDecimal.valueOf(22))
                .maxCo(BigDecimal.valueOf(33))
                .minCo(BigDecimal.valueOf(11))
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
        List<Stats> expectedResult = IntStream.range(1, 10)
                .map(i -> 10 - i)
                .mapToObj(i -> Stats.builder().cityId(new UUID(0, i)).average(BigDecimal.valueOf(31 + i / 10)).build())
                .toList();
        //when
        List<Stats> result = statsRepository.getLastMonthTop10NOUsage(LocalDateTime.of(2024, 12, 1, 12, 0).toInstant(ZoneOffset.UTC));
        //then
        assertEquals(expectedResult, result);
    }
}
