package com.bunshock.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter @Setter
@SuperBuilder
public class ResponseErrorDTO extends ResponseDTO {

    // Request URI
    private String apiPath;

    private List<String> errors;

}
