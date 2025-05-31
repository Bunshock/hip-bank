package com.bunshock.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
public class ResponseSuccessDTO<T> extends ResponseDTO {

    private String message;
    private T data;

    public ResponseSuccessDTO(String message, T data, ResponseDTOBuilder<?, ?> b) {
        super(b);
        this.message = message;
        this.data = data;
    }

}
