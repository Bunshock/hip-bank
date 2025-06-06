package com.bunshock.accounts.dto.account;

import com.bunshock.accounts.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class AccountShowDTO {

    private String accountNumber;
    private AccountType accountType;
    private String branchAddress;

}
