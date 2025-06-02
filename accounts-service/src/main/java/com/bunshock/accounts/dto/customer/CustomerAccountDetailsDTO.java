package com.bunshock.accounts.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class CustomerAccountDetailsDTO {

    private String name;
    private String email;
    private String mobileNumber;
    private String accountNumber;
    private String accountType;
    private String branchAddress;

}
