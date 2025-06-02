package com.bunshock.accounts.mapper;

import com.bunshock.accounts.dto.account.AccountShowDTO;
import com.bunshock.accounts.entity.Account;
import com.bunshock.accounts.enums.AccountType;

public class AccountMapper {

    public static AccountShowDTO mapToAccountShowDTO(Account account) {
        return AccountShowDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(AccountType.valueOf(account.getAccountType().name()))
                .branchAddress(account.getBranchAddress())
                .build();
    }

}
