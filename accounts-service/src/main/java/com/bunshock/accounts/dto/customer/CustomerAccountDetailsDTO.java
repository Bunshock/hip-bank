package com.bunshock.accounts.dto.customer;

import com.bunshock.accounts.dto.account.AccountShowDTO;
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

    private AccountShowDTO account;

}
