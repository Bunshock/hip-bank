package com.bunshock.loans.dto.loan;

import com.bunshock.loans.validation.ValidLoanType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class LoanUpdateDTO {

    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @ValidLoanType
    private String loanType;

    @Positive
    private Double totalLoan;

    @PositiveOrZero
    private Double amountPaid;

    @PositiveOrZero
    private Double outstandingAmount;

}
