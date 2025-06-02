package com.bunshock.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter @Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseErrorDTO extends ResponseDTO {

    // Request URI
    private String apiPath;

    // For single message error (like common exceptions)
    private String errorMessage;

    // For catching multiple error messages (like validation errors)
    private Map<String, String> errors;

}
