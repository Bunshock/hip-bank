package com.bunshock.accounts.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Schema(
        name = "API Response",
        description = "Schema for base API Response"
)
@Getter @Setter
@SuperBuilder
public abstract class ResponseDTO {

    @Schema(
            description = "HTTP status code of the response",
            example = "201"
    )
    private int statusCode;

    @Schema(
            description = "Timestamp of the response. Format: dd-MM-yyyy HH:mm",
            example = "02-06-2025 23:03"
    )
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp;

}
