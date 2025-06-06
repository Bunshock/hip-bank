package com.bunshock.loans.service;

import com.bunshock.loans.dto.loan.LoanCreateDTO;
import com.bunshock.loans.dto.loan.LoanShowDTO;
import com.bunshock.loans.dto.loan.LoanUpdateDTO;

import java.util.List;

public interface ILoanService {

    /**
     * Creates a new loan
     *
     * @param loanInput the details of the new loan
     */
    void createLoan(LoanCreateDTO loanInput);

    /**
     * Fetches the details for all loans
     *
     * @return a list of all loans
     */
    List<LoanShowDTO> fetchAllLoans();


    /**
     * Fetches the loan details for the given loan number
     *
     * @param loanNumber the loan number of the loan
     * @return the loan details
     */
    LoanShowDTO fetchLoan(String loanNumber);

    /**
     * Updates the loan details for the given loan number
     *
     * @param loanNumber the loan number of the loan
     * @param updatedLoan the updated loan details
     * @return the updated loan details
     */
    LoanShowDTO updateLoan(String loanNumber, LoanUpdateDTO updatedLoan);


    /**
     * Deletes the loan with the given loan number
     *
     * @param loanNumber the loan number of the loan
     */
    void deleteLoan(String loanNumber);

}
