package org.example.demo.service.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.example.demo.exception.ExternalDependencyException;
import org.example.demo.model.RegionDto;
import org.junit.jupiter.api.BeforeEach;
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
    void testIsValidRegionForTheCityWhenSuccessResponseRegionValidExpectFalse() {
        //given
        setUpWiremockSuccessResponse(cityId, RegionDto.builder().regionId(regionId).build());
        //when
        boolean result = regionService.isValidRegionForTheCity(regionId, cityId);
        //then
        assertTrue(result);
    }

    @Test
    void testIsValidRegionForTheCityWhenSuccessResponseRegionNotValidExpectTrue() {
        //given
        setUpWiremockSuccessResponse(cityId, RegionDto.builder().regionId(UUID.randomUUID()).build());
        //when
        boolean result = regionService.isValidRegionForTheCity(regionId, cityId);
        //then
        assertFalse(result);
    }

    @Test
    void testIsValidRegionForTheCityWhenErrorResponseExpectException() {
        //given
        setUpWiremockErrorResponse(cityId);
        //when
        //then
        assertThrows(ExternalDependencyException.class, () -> regionService.isValidRegionForTheCity(regionId, cityId));
    }

    @Test
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
