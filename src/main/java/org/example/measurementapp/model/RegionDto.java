package org.example.measurementapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Region data transfer object, used for communication with external region API.
 */
@Data
@Builder
@AllArgsConstructor
public class RegionDto {

    private String country;
    private String city;
    private String region;
    private UUID regionId;
}
