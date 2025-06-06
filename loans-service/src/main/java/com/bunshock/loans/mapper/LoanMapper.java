package com.bunshock.loans.mapper;

import com.bunshock.loans.dto.loan.LoanShowDTO;
import com.bunshock.loans.entity.Loan;

public class LoanMapper {

    public static LoanShowDTO mapToLoanShowDTO(Loan loan) {
        return LoanShowDTO.builder()
                .mobileNumber(loan.getMobileNumber())
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .totalLoan(loan.getTotalLoan())
                .amountPaid(loan.getAmountPaid())
                .outstandingAmount(loan.getOutstandingAmount())
                .build();
    }

}
