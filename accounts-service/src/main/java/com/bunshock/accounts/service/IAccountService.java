package com.bunshock.accounts.service;

import com.bunshock.accounts.dto.customer.CustomerAccountDetailsDTO;
import com.bunshock.accounts.dto.customer.CustomerAccountUpdateDTO;
import com.bunshock.accounts.dto.customer.CustomerInputDTO;

import java.util.List;

public interface IAccountService {

    /**
     * Creates an account for the given customer
     *
     * @param customerInput the customer details
     */
    void createAccount(CustomerInputDTO customerInput);

    /**
     * Fetches the details for all customer accounts.
     *
     * @return a list of customer account details
     */
    List<CustomerAccountDetailsDTO> fetchAllAccountDetails();

    /**
     * Fetches the account details for a customer with a given mobile number
     *
     * @param mobileNumber the mobile number of the customer
     * @return the account details
     */
    CustomerAccountDetailsDTO fetchAccountDetails(String mobileNumber);

    /**
     * Updates the information and/or account details for a customer with a given mobile number
     *
     * @param mobileNumber the mobile number of the customer
     * @param updatedAccount the updated account/customer details
     * @return the updated account details
     */
    CustomerAccountDetailsDTO updateAccount(String mobileNumber, CustomerAccountUpdateDTO updatedAccount);

    /**
     * Deletes the account for the given customer with the given mobile number.
     * This operation also deletes the customer entity from the customer table.
     *
     * @param mobileNumber the mobile number of the customer
     */
    void deleteAccount(String mobileNumber);
}
