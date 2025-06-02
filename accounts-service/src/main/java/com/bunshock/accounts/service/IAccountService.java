package com.bunshock.accounts.service;

import com.bunshock.accounts.dto.customer.CustomerAccountDetailsDTO;
import com.bunshock.accounts.dto.customer.CustomerInputDTO;

public interface IAccountService {

    /**
     * Creates an account for the given customer
     *
     * @param customerInput the customer details
     */
    void createAccount(CustomerInputDTO customerInput);

    /**
     * Fetches the account details for the given Customer's mobile number
     *
     * @param mobileNumber the mobile number of the customer
     * @return the account details
     */
    CustomerAccountDetailsDTO fetchAccountDetails(String mobileNumber);
}
