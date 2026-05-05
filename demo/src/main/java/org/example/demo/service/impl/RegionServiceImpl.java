package org.example.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.model.RegionDto;
import org.example.demo.service.RegionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Main implementation of the RegionService interface.
 */
@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class RegionServiceImpl implements RegionService {

    private final WebClient webClient;

    @Override
    @Cacheable(value = "region-city-matching", key = "#regionId + ' ' + #cityId")
    public boolean isValidRegionForTheCity(UUID regionId, UUID cityId) {
        return Boolean.TRUE.equals(getRegionDtoFlux(cityId)
                .map(regionDto -> regionDto.getRegionId().equals(regionId))
                .doOnNext(regionDto -> log.info("RegionDto received from Region service: {}", regionDto))
                .block());
    }

    @Override
    public RegionDto getRegion(UUID cityId) {
        return getRegionDtoFlux(cityId)
                .onErrorResume(err -> Mono.empty())
                .block();
    }

    private Mono<RegionDto> getRegionDtoFlux(UUID cityId) {
        return webClient.get()
                .uri("cities/{cityId}", cityId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RegionDto.class);
    }
}
