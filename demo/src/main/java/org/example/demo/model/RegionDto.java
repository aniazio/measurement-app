package org.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class RegionDto {

    private String country;
    private String city;
    private String region;
    private UUID regionId;
}
