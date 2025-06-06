package com.bunshock.loans.service.impl;

import com.bunshock.loans.constants.LoanConstants;
import com.bunshock.loans.dto.loan.LoanCreateDTO;
import com.bunshock.loans.dto.loan.LoanShowDTO;
import com.bunshock.loans.dto.loan.LoanUpdateDTO;
import com.bunshock.loans.entity.Loan;
import com.bunshock.loans.enums.LoanType;
import com.bunshock.loans.exception.IdGenerationException;
import com.bunshock.loans.exception.ResourceNotFoundException;
import com.bunshock.loans.mapper.LoanMapper;
import com.bunshock.loans.repository.ILoanRepository;
import com.bunshock.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanService implements ILoanService {

    private final ILoanRepository loanRepository;
    private final LoanNumberGeneratorService loanNumberGenerator;

    /**
     * Generates a unique loan number by attempting a number of times to produce
     * a new loan number that doesn't exist in the repository.
     *
     * @return a unique loan number as a String
     * @throws IdGenerationException if unable to generate a unique loan number
     *         after the specified number of attempts
     */
    private String generateUniqueLoanNumber() {
        int maxAttempts = 10;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            String newLoanNumber = loanNumberGenerator.generateId();
            if (!loanRepository.existsByLoanNumber(newLoanNumber)) {
                return newLoanNumber;
            }
        }
        throw new IdGenerationException("Failed to generate a unique loan" +
                " number after " + maxAttempts + " attempts");
    }

    @Override
    public void createLoan(LoanCreateDTO loanInput) {
        // TODO: Outstanding amount should be calculated as the difference
        //  between total loan and amount paid (initially, it will be total loan)
        Loan loan = Loan.builder()
                .mobileNumber(loanInput.getMobileNumber())
                .loanNumber(generateUniqueLoanNumber())
                .loanType(LoanType.valueOf(loanInput.getLoanType()))
                .totalLoan(LoanConstants.NEW_LOAN_LIMIT)
                .amountPaid(0.0)
                .outstandingAmount(LoanConstants.NEW_LOAN_LIMIT)
                .build();
        loanRepository.save(loan);
    }

    @Override
    public List<LoanShowDTO> fetchAllLoans() {
        return loanRepository.findAll().stream()
                .map(LoanMapper::mapToLoanShowDTO)
                .toList();
    }

    @Override
    public LoanShowDTO fetchLoan(String loanNumber) {
        Loan loan = loanRepository.findByLoanNumber(loanNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber));
        return LoanMapper.mapToLoanShowDTO(loan);
    }

    @Override
    public LoanShowDTO updateLoan(String loanNumber, LoanUpdateDTO updatedLoan) {
        Loan loan = loanRepository.findByLoanNumber(loanNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber));

        if (updatedLoan.getMobileNumber() != null)
            loan.setMobileNumber(updatedLoan.getMobileNumber());
        if (updatedLoan.getLoanType() != null)
            loan.setLoanType(LoanType.valueOf(updatedLoan.getLoanType()));
        if (updatedLoan.getTotalLoan() != null)
            loan.setTotalLoan(updatedLoan.getTotalLoan());
        if (updatedLoan.getAmountPaid() != null)
            loan.setAmountPaid(updatedLoan.getAmountPaid());
        
        // TODO: Update outstanding amount depending on two cases:
        //  1. Total loan is updated
        //  2. Amount paid is updated
        //  Outstanding amount should be calculated as total loan - amount paid
        if (updatedLoan.getOutstandingAmount() != null)
            loan.setOutstandingAmount(updatedLoan.getOutstandingAmount());

        return LoanMapper.mapToLoanShowDTO(loanRepository.save(loan));
    }

    @Override
    public void deleteLoan(String loanNumber) {
        Loan loan = loanRepository.findByLoanNumber(loanNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber));
        loanRepository.delete(loan);
    }

}
