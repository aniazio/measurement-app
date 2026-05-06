package org.example.measurementapp.exception.handler;

import org.example.measurementapp.exception.ExternalDependencyException;
import org.example.measurementapp.exception.InvalidRegionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.UUID;

import static org.example.measurementapp.exception.handler.GlobalExceptionHandler.INVALID_REGION_OR_CITY_ID;
import static org.example.measurementapp.exception.handler.GlobalExceptionHandler.MEASUREMENT_ALREADY_RECORDED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("handleInvalidRegionException when invoked expects generic problem detail without region and city id")
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
    @DisplayName("handleDuplicateKeyException when invoked expects generic problem detail without sensitive information from the message")
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

    @Test
    @DisplayName("handleExternalDependencyException when invoked expects problem detail with message from exception")
    void testHandleExternalDependencyExceptionWhenInvokedExpectProblemDetail() {
        //given
        ExternalDependencyException externalDependencyException = new ExternalDependencyException();
        ProblemDetail expected = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, externalDependencyException.getMessage());
        //when
        ProblemDetail result = globalExceptionHandler.handleExternalDependencyException(externalDependencyException);
        //then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("handleMethodArgumentNotValidException when invoked expects problem detail with message explaining validation errors")
    void testHandleMethodArgumentNotValidExceptionWhenInvokedExpectProblemDetail() {
        //given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        FieldError fieldError = mock(FieldError.class);
        given(fieldError.getField()).willReturn("fieldName");
        given(fieldError.getDefaultMessage()).willReturn("errorMessage");
        BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.getFieldErrors()).willReturn(List.of(fieldError));
        given(exception.getBindingResult()).willReturn(bindingResult);
        ProblemDetail expected = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "fieldName: errorMessage");
        expected.setTitle("Validation failed");
        //when
        ProblemDetail result = globalExceptionHandler.handleMethodArgumentNotValidException(exception);
        //then
        assertEquals(expected, result);
    }
}
