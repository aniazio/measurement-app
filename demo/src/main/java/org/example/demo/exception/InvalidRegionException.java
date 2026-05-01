package org.example.demo.exception;

import java.util.UUID;

public class InvalidRegionException extends IllegalArgumentException {

    public InvalidRegionException(UUID region, UUID city) {
        super("Provided region " + region + " is not matching city " + city);
    }
}
