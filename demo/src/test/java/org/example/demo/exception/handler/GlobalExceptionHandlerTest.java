package org.example.demo.exception.handler;

import org.example.demo.exception.InvalidRegionException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.UUID;

import static org.example.demo.exception.handler.GlobalExceptionHandler.INVALID_REGION_OR_CITY_ID;
import static org.example.demo.exception.handler.GlobalExceptionHandler.MEASUREMENT_ALREADY_RECORDED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleInvalidRegionExceptionWhenInvokedExpectGenericProblemDetail() {
        //given
        UUID regionId = UUID.randomUUID();
        UUID cityId = UUID.randomUUID();
        InvalidRegionException input = new InvalidRegionException(regionId, cityId);
        ProblemDetail expected = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, INVALID_REGION_OR_CITY_ID);
        //when
        ProblemDetail result = globalExceptionHandler.handleInvalidRegionException(input);
        //then
        assertEquals(expected, result);
        assertFalse(result.toString().contains(regionId.toString()));
        assertFalse(result.toString().contains(cityId.toString()));
    }

    @Test
    void testHandleDuplicateKeyExceptionWhenInvokedExpectGenericProblemDetail() {
        //given
        String message = "sensitive info";
        DuplicateKeyException duplicateKeyException = new DuplicateKeyException(message);
        ProblemDetail expected = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, MEASUREMENT_ALREADY_RECORDED);
        //when
        ProblemDetail result = globalExceptionHandler.handleDuplicateKeyException(duplicateKeyException);
        //then
        assertEquals(expected, result);
        assertFalse(result.toString().contains(message));
    }
}
