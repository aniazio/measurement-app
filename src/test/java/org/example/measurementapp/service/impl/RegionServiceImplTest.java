package org.example.measurementapp.service.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.example.measurementapp.exception.ExternalDependencyException;
import org.example.measurementapp.model.RegionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegionServiceImplTest {

    @RegisterExtension
    static final WireMockExtension wireMock = WireMockExtension.newInstance().build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private RegionServiceImpl regionService;
    private UUID cityId, regionId;

    @BeforeEach
    void setUp() {
        regionService = new RegionServiceImpl(WebClient.builder().baseUrl(wireMock.baseUrl()).build());
        cityId = UUID.randomUUID();
        regionId = UUID.randomUUID();
    }

    @Test
    @DisplayName("isValidRegionForTheCity when success response region valid expects true")
    void testIsValidRegionForTheCityWhenSuccessResponseRegionValidExpectTrue() {
        //given
        setUpWiremockSuccessResponse(cityId, RegionDto.builder().regionId(regionId).build());
        //when
        boolean result = regionService.isValidRegionForTheCity(regionId, cityId);
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("isValidRegionForTheCity when success response region not valid expects false")
    void testIsValidRegionForTheCityWhenSuccessResponseRegionNotValidExpectFalse() {
        //given
        setUpWiremockSuccessResponse(cityId, RegionDto.builder().regionId(UUID.randomUUID()).build());
        //when
        boolean result = regionService.isValidRegionForTheCity(regionId, cityId);
        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("isValidRegionForTheCity when error response expects exception")
    void testIsValidRegionForTheCityWhenErrorResponseExpectException() {
        //given
        setUpWiremockErrorResponse(cityId);
        //when
        //then
        assertThrows(ExternalDependencyException.class, () -> regionService.isValidRegionForTheCity(regionId, cityId));
    }

    @Test
    @DisplayName("getRegion when successful response from external service expects region dto")
    void testGetRegionWhenSuccessfulResponseFromExternalServiceExpectRegionDto() {
        //given
        RegionDto regionDto = RegionDto.builder()
                .regionId(regionId)
                .city("Some city")
                .region("Some region")
                .country("Some country")
                .build();
        setUpWiremockSuccessResponse(cityId, regionDto);
        //when
        RegionDto result = regionService.getRegion(cityId);
        //then
        assertEquals(regionDto, result);
    }

    @Test
    @DisplayName("getRegion when error from external service expects empty dto")
    void testGetRegionWhenErrorFromExternalServiceExpectEmptyDto() {
        //given
        setUpWiremockErrorResponse(cityId);
        //when
        RegionDto result = regionService.getRegion(regionId);
        //then
        assertEquals(RegionDto.builder().build(), result);
    }

    private void setUpWiremockSuccessResponse(UUID cityId, RegionDto regionDto) {
        wireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/cities/" + cityId))
                .willReturn(WireMock.ok()
                        .withBody(OBJECT_MAPPER.writeValueAsString(regionDto))
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())));
    }

    private void setUpWiremockErrorResponse(UUID cityId) {
        wireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/cities/" + cityId))
                .willReturn(WireMock.serverError()));
    }
}
