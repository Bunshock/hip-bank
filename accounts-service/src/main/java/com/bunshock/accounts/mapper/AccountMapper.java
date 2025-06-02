package com.bunshock.accounts.mapper;

import com.bunshock.accounts.dto.account.AccountShowDTO;
import com.bunshock.accounts.entity.Account;

public class AccountMapper {

    public static AccountShowDTO mapToAccountShowDTO(Account account) {
        return AccountShowDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }

}
