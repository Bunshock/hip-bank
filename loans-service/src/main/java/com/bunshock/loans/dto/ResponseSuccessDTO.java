package com.bunshock.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Schema(
        name = "API Response: Success",
        description = "API response for a successful request"
)
@Getter @Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSuccessDTO<T> extends ResponseDTO {

    @Schema(
            description = "Message of the response",
            example = "Account created successfully"
    )
    private String message;

    @Schema(
            description = "Data of the response",
            example = "{}"
    )
    private T data;

}
