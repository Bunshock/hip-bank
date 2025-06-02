package com.bunshock.accounts.mapper;

import com.bunshock.accounts.dto.customer.CustomerInputDTO;
import com.bunshock.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerInputDTO customerInput) {
        return Customer.builder()
                .name(customerInput.getName())
                .email(customerInput.getEmail())
                .mobileNumber(customerInput.getMobileNumber())
                .build();
    }

}