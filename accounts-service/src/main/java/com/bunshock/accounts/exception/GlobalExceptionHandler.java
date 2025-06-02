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

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ResponseErrorDTO> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException e, WebRequest request
    ) {
        return new ResponseEntity<>(ResponseErrorDTO.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .apiPath(request.getDescription(false))
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IdGenerationException.class)
    public ResponseEntity<ResponseErrorDTO> handleIdGenerationException(
            CustomerAlreadyExistsException e, WebRequest request
    ) {
        return new ResponseEntity<>(ResponseErrorDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .apiPath(request.getDescription(false))
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
