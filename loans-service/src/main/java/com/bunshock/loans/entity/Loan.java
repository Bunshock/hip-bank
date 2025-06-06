package com.bunshock.loans.entity;

import com.bunshock.loans.enums.LoanType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "loans")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@SuperBuilder
public class Loan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id", unique = true, nullable = false)
    private Long loanId;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "loan_number", nullable = false)
    private String loanNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @Column(name = "total_loan", nullable = false)
    private Double totalLoan;

    @Column(name = "amount_paid", nullable = false)
    private Double amountPaid;

    @Column(name = "outstanding_amount", nullable = false)
    private Double outstandingAmount;

}
