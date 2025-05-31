package com.bunshock.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter @Setter
@SuperBuilder
public class ResponseErrorDTO extends ResponseDTO {

    private String apiPath;
    private List<String> errors;

    public ResponseErrorDTO(String apiPath, List<String> errors, ResponseDTOBuilder<?, ?> b) {
        super(b);
        this.apiPath = apiPath;
        this.errors = errors;
    }

}
