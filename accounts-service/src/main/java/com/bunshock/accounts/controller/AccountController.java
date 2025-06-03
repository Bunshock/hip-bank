package com.bunshock.accounts.controller;

import com.bunshock.accounts.constants.AccountConstants;
import com.bunshock.accounts.dto.customer.CustomerAccountDetailsDTO;
import com.bunshock.accounts.dto.customer.CustomerAccountUpdateDTO;
import com.bunshock.accounts.dto.customer.CustomerInputDTO;
import com.bunshock.accounts.dto.ResponseDTO;
import com.bunshock.accounts.dto.ResponseSuccessDTO;
import com.bunshock.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountController {

    private final IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccounts(
            @Valid @RequestBody CustomerInputDTO customerInput
    ) {
        accountService.createAccount(customerInput);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_201, "Account"))
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ResponseDTO> fetchAccounts() {
        return new ResponseEntity<>(ResponseSuccessDTO.<List<CustomerAccountDetailsDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Fetched all account details"))
                .data(accountService.fetchAllAccountDetails())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/fetch/{mobileNumber}")
    public ResponseEntity<ResponseDTO> fetchAccountDetails(
            @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
                    "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
            @PathVariable String mobileNumber
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<CustomerAccountDetailsDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Fetched account details " +
                        "for customer with mobile number: " + mobileNumber))
                .data(accountService.fetchAccountDetails(mobileNumber))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{mobileNumber}")
    public ResponseEntity<ResponseDTO> updateCustomerAccount(
            @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
                    "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
            @PathVariable String mobileNumber,
            @Valid @RequestBody CustomerAccountUpdateDTO updatedAccount
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<CustomerAccountDetailsDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Updated account details " +
                        "for customer with mobile number: " + mobileNumber))
                .data(accountService.updateAccount(mobileNumber, updatedAccount))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{mobileNumber}")
    @Transactional
    public ResponseEntity<ResponseDTO> deleteCustomerAccount(
            @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
                    "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
            @PathVariable String mobileNumber
    ) {
        accountService.deleteAccount(mobileNumber);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Deleted account details " +
                        "for customer with mobile number: " + mobileNumber))
                .build(), HttpStatus.OK);
    }

}
