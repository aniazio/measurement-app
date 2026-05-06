package org.example.measurementapp.exception;

import java.util.UUID;

/**
 * Exception thrown when provided region is not matching city.
 */
public class InvalidRegionException extends IllegalArgumentException {

    /**
     * Constructor with generic message.
     *
     * @param region region id
     * @param city   city id
     */
    public InvalidRegionException(UUID region, UUID city) {
        super("Provided region " + region + " is not matching city " + city);
    }
}
