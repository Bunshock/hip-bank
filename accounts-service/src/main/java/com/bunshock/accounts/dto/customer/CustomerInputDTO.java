package com.bunshock.accounts.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CustomerInputDTO {

    private String name;
    private String email;
    private String mobileNumber;

}
