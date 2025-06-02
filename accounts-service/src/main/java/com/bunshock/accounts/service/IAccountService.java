package com.bunshock.accounts.service;

import com.bunshock.accounts.dto.customer.CustomerInputDTO;

public interface IAccountService {

    /**
     * Creates an account for the given customer
     *
     * @param customerInput the customer details
     */
    void createAccount(CustomerInputDTO customerInput);

}
