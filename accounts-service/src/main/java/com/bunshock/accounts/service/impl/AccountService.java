package com.bunshock.accounts.service.impl;

import com.bunshock.accounts.constants.AccountConstants;
import com.bunshock.accounts.dto.customer.CustomerAccountDetailsDTO;
import com.bunshock.accounts.dto.customer.CustomerInputDTO;
import com.bunshock.accounts.entity.Account;
import com.bunshock.accounts.entity.Customer;
import com.bunshock.accounts.exception.CustomerAlreadyExistsException;
import com.bunshock.accounts.exception.IdGenerationException;
import com.bunshock.accounts.exception.ResourceNotFoundException;
import com.bunshock.accounts.mapper.CustomerMapper;
import com.bunshock.accounts.repository.IAccountRepository;
import com.bunshock.accounts.repository.ICustomerRepository;
import com.bunshock.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;
    private final AccountNumberGeneratorService accountNumberGenerator;

    /**
     * Generates a unique account number by attempting a number of times to produce
     * a new account number that doesn't exist in the repository.
     *
     * @return a unique account number as a String
     * @throws IdGenerationException if unable to generate a unique account number
     *         after the specified number of attempts
     */
    private String generateUniqueAccountNumber() {
        int maxAttempts = 10;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            String newAccountNumber = accountNumberGenerator.generateId();
            if (!accountRepository.existsById(newAccountNumber)) {
                return newAccountNumber;
            }
        }
        throw new IdGenerationException("Failed to generate a unique account" +
                " number after " + maxAttempts + " attempts");
    }

    @Override
    public void createAccount(CustomerInputDTO customerInput) {
        // Create new customer using input data
        Customer customer = CustomerMapper.mapToCustomer(customerInput);

        // Customer registration is done via mobile number. It should be unique
        // NOTE: For now, we don't allow an existing customer to be associated with
        // multiple accounts. Nor do we allow an existing customer to be reassigned
        // to a newly created account
        customerRepository.findByMobileNumber(customer.getMobileNumber())
                .ifPresent(existingCustomer -> {
                    throw new CustomerAlreadyExistsException("Customer with mobile" +
                            " number '" + customer.getMobileNumber() + "' already exists");
                } );

        // TODO: properly set createdBy using Spring Security context
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);

        // Create new account associated with newly saved customer
        Account newAccount = new Account();

        String accountNumber = generateUniqueAccountNumber();
        newAccount.setAccountNumber(accountNumber);

        newAccount.setCustomerId(savedCustomer.getCustomerId());
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        accountRepository.save(newAccount);
    }

    @Override
    public CustomerAccountDetailsDTO fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer", "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account", "customerId", customer.getCustomerId().toString()));

        return CustomerMapper.mapToCustomerAccountDetailsDTO(customer, account);
    }

}
