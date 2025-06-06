package com.bunshock.loans.repository;

import com.bunshock.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByLoanNumber(String loanNumber);

    Optional<Loan> findByLoanNumber(String loanNumber);

}
