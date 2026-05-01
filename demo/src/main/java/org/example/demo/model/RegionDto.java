package org.example.demo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class RegionDto {

    private String country;
    private String city;
    private String region;
    private UUID regionId;
}
