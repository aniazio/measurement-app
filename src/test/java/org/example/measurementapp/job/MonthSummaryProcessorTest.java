package org.example.measurementapp.job;

import org.example.measurementapp.model.RegionDto;
import org.example.measurementapp.model.Stats;
import org.example.measurementapp.repository.StatsRepository;
import org.example.measurementapp.service.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.example.measurementapp.job.MonthSummaryProcessor.DATE_FORMAT;
import static org.example.measurementapp.job.MonthSummaryProcessor.FILE_NAME_PATTERN;
import static org.example.measurementapp.job.MonthSummaryProcessor.HEADER_ROW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MonthSummaryProcessorTest {

    @Mock
    private StatsRepository statsRepository;
    @Mock
    private RegionService regionService;
    @InjectMocks
    private MonthSummaryProcessor monthSummaryProcessor;
    @TempDir
    private Path path;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(monthSummaryProcessor, "absoluteDirectory", path.toAbsolutePath().toString());
    }

    @Test
    @DisplayName("init when triggered expects prepare reports directory")
    void testInitWhenTriggeredExpectPrepareReportsDirectory(@TempDir Path tempDir) {
        ReflectionTestUtils.setField(monthSummaryProcessor, "absoluteDirectory", tempDir.toString());
        Path path = tempDir.getParent();

        monthSummaryProcessor.init();

        assertTrue(Files.exists(path));
        assertTrue(Files.isDirectory(path.toAbsolutePath()));
    }

    @Test
    @DisplayName("produceMonthSummaryReport when no input date and no data in db expects produce empty report for last month")
    void testProduceMonthSummaryReportWhenNoDateNoDataExpectProduceEmptyReportForLastMonth() throws IOException {
        //given
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        Instant firstDayLastMonth = LocalDateTime.of(lastMonth.getYear(), lastMonth.getMonth(), 1, 0, 0)
                .toInstant(ZoneOffset.UTC);
        given(statsRepository.getLastMonthTop10NOUsage(firstDayLastMonth))
                .willReturn(List.of());
        //when
        monthSummaryProcessor.produceMonthSummaryReport();
        //then
        Path expectedFile = path.resolve(String.format(FILE_NAME_PATTERN, DateTimeFormatter.ofPattern(DATE_FORMAT).format(lastMonth)));
        assertTrue(Files.exists(expectedFile.toAbsolutePath()));
        String content = Files.readString(expectedFile);
        assertEquals(HEADER_ROW + "\n", content);
    }

    @Test
    @DisplayName("produceMonthSummaryReport when no input date and some data in db expects produce report for last month")
    void testProduceMonthSummaryReportWhenNoDateSomeStatsExpectProduceReportForLastMonth() throws IOException {
        //given
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        Instant firstDayLastMonth = LocalDateTime.of(lastMonth.getYear(), lastMonth.getMonth(), 1, 0, 0)
                .toInstant(ZoneOffset.UTC);
        Stats stat1 = createStats(BigDecimal.valueOf(12.3));
        Stats stat2 = createStats(BigDecimal.valueOf(11.1));
        given(statsRepository.getLastMonthTop10NOUsage(firstDayLastMonth))
                .willReturn(List.of(stat1, stat2));
        given(regionService.getRegion(stat1.getCityId())).willReturn(RegionDto.builder().city("City1").region("Region1").build());
        given(regionService.getRegion(stat2.getCityId())).willReturn(RegionDto.builder().city("City2").region("Region2").build());
        //when
        monthSummaryProcessor.produceMonthSummaryReport();
        //then
        Path expectedFile = path.resolve(String.format(FILE_NAME_PATTERN, DateTimeFormatter.ofPattern(DATE_FORMAT).format(lastMonth)));
        assertTrue(Files.exists(expectedFile.toAbsolutePath()));
        String content = Files.readString(expectedFile);
        assertEquals(HEADER_ROW + "\n" + "City1,Region1,12.3" + "\n" + "City2,Region2,11.1" + "\n", content);
    }

    @Test
    @DisplayName("produceMonthSummaryReport when input date and no data in db expects produce empty report for selected month")
    void testProduceMonthSummaryReportWhenDateProvidedNoDataExpectProduceEmptyReportForSelectedMonth() throws IOException {
        //given
        LocalDateTime inputDate = LocalDateTime.of(2018, 12, 12, 0, 0);
        Instant firstDayLastMonth = LocalDateTime.of(2018, 12, 1, 0, 0)
                .toInstant(ZoneOffset.UTC);
        given(statsRepository.getLastMonthTop10NOUsage(firstDayLastMonth))
                .willReturn(List.of());
        //when
        monthSummaryProcessor.produceMonthSummaryReport(inputDate);
        //then
        Path expectedFile = path.resolve(String.format(FILE_NAME_PATTERN, "201812"));
        assertTrue(Files.exists(expectedFile.toAbsolutePath()));
        String content = Files.readString(expectedFile);
        assertEquals(HEADER_ROW + "\n", content);
    }

    @Test
    @DisplayName("produceMonthSummaryReport when input date and some data in db expects produce report for selected month")
    void testProduceMonthSummaryReportWhenDateProvidedSomeStatsExpectProduceReportForSelectedMonth() throws IOException {
        //given
        LocalDateTime inputDate = LocalDateTime.of(2018, 12, 12, 0, 0);
        Instant firstDayLastMonth = LocalDateTime.of(2018, 12, 1, 0, 0)
                .toInstant(ZoneOffset.UTC);
        Stats stat1 = createStats(BigDecimal.valueOf(12.3));
        Stats stat2 = createStats(BigDecimal.valueOf(11.1));
        given(statsRepository.getLastMonthTop10NOUsage(firstDayLastMonth))
                .willReturn(List.of(stat1, stat2));
        given(regionService.getRegion(stat1.getCityId())).willReturn(RegionDto.builder().city("City1").region("Region1").build());
        given(regionService.getRegion(stat2.getCityId())).willReturn(RegionDto.builder().city("City2").region("Region2").build());
        //when
        monthSummaryProcessor.produceMonthSummaryReport(inputDate);
        //then
        Path expectedFile = path.resolve(String.format(FILE_NAME_PATTERN, "201812"));
        assertTrue(Files.exists(expectedFile.toAbsolutePath()));
        String content = Files.readString(expectedFile);
        assertEquals(HEADER_ROW + "\n" + "City1,Region1,12.3" + "\n" + "City2,Region2,11.1" + "\n", content);
    }

    private Stats createStats(BigDecimal avg) {
        return Stats.builder()
                .cityId(UUID.randomUUID())
                .average(avg)
                .build();
    }
}
