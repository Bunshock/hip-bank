package com.bunshock.loans.dto.loan;

import com.bunshock.loans.validation.ValidLoanType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Loan: Update",
        description = "Schema for updating a loan"
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class LoanUpdateDTO {

    @Schema(
            description = "Mobile number of the customer",
            example = "+2211345678"
    )
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @Schema(
            description = "Type of loan. Enum: PERSONAL, BUSINESS, STUDENT, MORTGAGE, AUTO",
            example = "PERSONAL"
    )
    @ValidLoanType
    private String loanType;

    @Schema(
            description = "Total loan amount",
            example = "20000.0"
    )
    @Positive
    private Double totalLoan;

    @Schema(
            description = "Amount paid",
            example = "18500.0"
    )
    @PositiveOrZero
    private Double amountPaid;

    @Schema(
            description = "Outstanding amount",
            example = "1500.0"
    )
    @PositiveOrZero
    private Double outstandingAmount;

}
