package com.bunshock.accounts.dto;

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
            example = "Customer with mobile number '+254712345678' already exists"
    )
    private String errorMessage;

    // For catching multiple error messages (like validation errors)
    @Schema(
            description = "Map of error messages (multiple-error case)",
            example = "{\"name\":\"Name must be between 3 and 50 characters\"" +
                    ", \"email\":\"Invalid email address format\"}"
    )
    private Map<String, String> errors;

}
