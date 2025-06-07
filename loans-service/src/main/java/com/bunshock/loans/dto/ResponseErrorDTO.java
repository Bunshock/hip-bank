package com.bunshock.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Schema(
        name = "API Response: Error",
        description = "API response for error messages"
)
@Getter @Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseErrorDTO extends ResponseDTO {

    // Request URI
    @Schema(
            description = "API path where the error occurred",
            example = "uri=/api/accounts/create"
    )
    private String apiPath;


    // For single message error (like common exceptions)
    @Schema(
            description = "Error message (single-error case)",
            example = "updateCard.cardNumber: Card number must be exactly 10 digits"
    )
    private String errorMessage;

    // For catching multiple error messages (like validation errors)
    @Schema(
            description = "Map of error messages (multiple-error case)",
            example = "{\"cardType\":\"Invalid card type\",\"cardLimit\":\"must be greater than 0\"}"
    )
    private Map<String, String> errors;

}
