package com.bunshock.accounts.exception;

import com.bunshock.accounts.dto.ResponseErrorDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Single error message response
    private ResponseEntity<ResponseErrorDTO> singleErrorResponse(
            HttpStatus httpStatus, String error, WebRequest request
    ) {
        return new ResponseEntity<>(ResponseErrorDTO.builder()
                .statusCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .apiPath(request.getDescription(false))
                .errorMessage(error)
                .build(), httpStatus);
    }

    // Multiple error messages response
    private ResponseEntity<ResponseErrorDTO> multipleErrorResponse(
            HttpStatus httpStatus, Map<String, String> errors, WebRequest request
    ) {
        return new ResponseEntity<>(ResponseErrorDTO.builder()
                .statusCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .apiPath(request.getDescription(false))
                .errors(errors)
                .build(), httpStatus);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ResponseErrorDTO> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException e, WebRequest request
    ) {
        return singleErrorResponse(HttpStatus.CONFLICT, e.getMessage(), request);
    }

    @ExceptionHandler(IdGenerationException.class)
    public ResponseEntity<ResponseErrorDTO> handleIdGenerationException(
            IdGenerationException e, WebRequest request
    ) {
        return singleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> handleResourceNotFoundException(
            ResourceNotFoundException e, WebRequest request
    ) {
        return singleErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), request);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, WebRequest request
    ) {
        Map<String, String> validationErrors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error ->
                validationErrors.put(((FieldError) error).getField(), error.getDefaultMessage())
        );

        return multipleErrorResponse(HttpStatus.BAD_REQUEST, validationErrors, request);
    }

    // Handle invalid input or malformed requests (like invalid input for enums)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseErrorDTO> handleInvalidInput(HttpMessageNotReadableException ex, WebRequest request) {
        Throwable cause = ex.getCause();
        Map<String, String> errors = new HashMap<>();

        if (cause instanceof InvalidFormatException formatEx) {
            String fieldName = formatEx.getPath().getFirst().getFieldName();
            String value = String.valueOf(formatEx.getValue());
            errors.put(fieldName, "Invalid value: '" + value + "'");
        } else {
            errors.put("request", "Malformed or unreadable input");
        }

        return multipleErrorResponse(HttpStatus.BAD_REQUEST, errors, request);
    }

    // Handle any other not specified exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDTO> handleException(
            Exception e, WebRequest request
    ) {
        return singleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request);
    }

}
