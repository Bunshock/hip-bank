package com.bunshock.accounts.controller;

import com.bunshock.accounts.constants.AccountConstants;
import com.bunshock.accounts.dto.customer.CustomerInputDTO;
import com.bunshock.accounts.dto.ResponseDTO;
import com.bunshock.accounts.dto.ResponseSuccessDTO;
import com.bunshock.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    // TODO: Add input validation
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccounts(
            @RequestBody CustomerInputDTO customerInput
    ) {
        accountService.createAccount(customerInput);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_201, "Account"))
                .data(null)
                .build(), HttpStatus.CREATED);
    }

}
