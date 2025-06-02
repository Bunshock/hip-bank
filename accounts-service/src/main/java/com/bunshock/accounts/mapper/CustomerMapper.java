package com.bunshock.accounts.mapper;

import com.bunshock.accounts.dto.customer.CustomerInputDTO;
import com.bunshock.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerInputDTO customerInput) {
        Customer customer = new Customer();

        customer.setName(customerInput.getName());
        customer.setEmail(customerInput.getEmail());
        customer.setMobileNumber(customerInput.getMobileNumber());

        return customer;
    }

}