package com.bunshock.accounts.exception;

import com.bunshock.accounts.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ResponseErrorDTO> singleErrorResponse(
            HttpStatus httpStatus, String error, WebRequest request
    ) {
        return new ResponseEntity<>(ResponseErrorDTO.builder()
                .statusCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .apiPath(request.getDescription(false))
                .errors(List.of(error))
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

}
