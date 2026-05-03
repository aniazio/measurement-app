package org.example.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo.model.RegionDto;
import org.example.demo.service.RegionService;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class RegionServiceImpl implements RegionService {

    private final WebClient webClient;

    @Override
    public boolean isValidRegion(UUID regionId, UUID cityId) {
        return Boolean.TRUE.equals(getRegionDtoFlux(cityId)
                .map(regionDto -> regionDto.getRegionId().equals(regionId))
                .onErrorResume(err -> Mono.just(false))
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
