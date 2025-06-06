package com.bunshock.loans.controller;

import com.bunshock.loans.constants.LoanConstants;
import com.bunshock.loans.dto.ResponseDTO;
import com.bunshock.loans.dto.ResponseSuccessDTO;
import com.bunshock.loans.dto.loan.LoanCreateDTO;
import com.bunshock.loans.dto.loan.LoanShowDTO;
import com.bunshock.loans.dto.loan.LoanUpdateDTO;
import com.bunshock.loans.service.ILoanService;
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
@RequestMapping(path = "/api/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class LoanController {

    private final ILoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoan(
            @Valid @RequestBody LoanCreateDTO loanInput
    ) {
        loanService.createLoan(loanInput);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_201, "Loan"))
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ResponseDTO> fetchAllLoans() {
        return new ResponseEntity<>(ResponseSuccessDTO.<List<LoanShowDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Fetched all loan details"))
                .data(loanService.fetchAllLoans())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/fetch/{loanNumber}")
    public ResponseEntity<ResponseDTO> fetchLoan(
            @Pattern(regexp = "^\\d{10}$", message = "Loan number must be exactly 10 digits")
            @PathVariable String loanNumber
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<LoanShowDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Fetched loan details for loan number "
                        + loanNumber))
                .data(loanService.fetchLoan(loanNumber))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{loanNumber}")
    public ResponseEntity<ResponseDTO> updateLoan(
            @Pattern(regexp = "^\\d{10}$", message = "Loan number must be exactly 10 digits")
            @PathVariable String loanNumber,
            @Valid @RequestBody LoanUpdateDTO updatedLoan
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<LoanShowDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Updated loan details for loan number "
                        + loanNumber))
                .data(loanService.updateLoan(loanNumber, updatedLoan))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{loanNumber}")
    public ResponseEntity<ResponseDTO> deleteLoan(
            @Pattern(regexp = "^\\d{10}$", message = "Loan number must be exactly 10 digits")
            @PathVariable String loanNumber
    ) {
        loanService.deleteLoan(loanNumber);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Deleted loan details for loan number "
                        + loanNumber))
                .build(), HttpStatus.OK);
    }

}
