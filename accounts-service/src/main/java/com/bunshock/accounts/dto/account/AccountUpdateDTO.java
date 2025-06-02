package com.bunshock.accounts.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class AccountUpdateDTO {

    // TODO: Add input validation
    private String accountType;
    private String branchAddress;

}
