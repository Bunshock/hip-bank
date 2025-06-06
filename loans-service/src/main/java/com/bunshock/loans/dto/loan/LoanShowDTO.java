package com.bunshock.loans.dto.loan;

import com.bunshock.loans.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class LoanShowDTO {

    private String mobileNumber;
    private String loanNumber;
    private LoanType loanType;
    private Double totalLoan;
    private Double amountPaid;
    private Double outstandingAmount;

}
