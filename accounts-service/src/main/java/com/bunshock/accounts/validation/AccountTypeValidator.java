package com.bunshock.accounts.validation;

import com.bunshock.accounts.enums.AccountType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountTypeValidator implements ConstraintValidator<ValidAccountType, String> {

    @Override
    public boolean isValid(String accountType, ConstraintValidatorContext context) {
        if (accountType == null) return true;
        try {
            AccountType.valueOf(accountType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
