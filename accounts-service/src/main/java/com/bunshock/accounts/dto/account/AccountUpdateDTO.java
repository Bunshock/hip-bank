package com.bunshock.accounts.dto.account;

import com.bunshock.accounts.validation.ValidAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class AccountUpdateDTO {

    // Keep accountType as String to allow for custom validation
    @ValidAccountType
    private String accountType;

    // TODO: Add validation
    private String branchAddress;

}
