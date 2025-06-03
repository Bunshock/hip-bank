package com.bunshock.accounts.mapper;

import com.bunshock.accounts.dto.customer.CustomerAccountDetailsDTO;
import com.bunshock.accounts.dto.customer.CustomerInputDTO;
import com.bunshock.accounts.entity.Account;
import com.bunshock.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerInputDTO customerInput) {
        return Customer.builder()
                .name(customerInput.getName())
                .email(customerInput.getEmail())
                .mobileNumber(customerInput.getMobileNumber())
                .build();
    }

    public static CustomerAccountDetailsDTO mapToCustomerAccountDetailsDTO(
            Customer customer, Account account
    ) {
        return CustomerAccountDetailsDTO.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .account(AccountMapper.mapToAccountShowDTO(account))
                .build();
    }

}