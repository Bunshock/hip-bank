package com.bunshock.loans.dto.loan;

import com.bunshock.loans.validation.ValidLoanType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Loan: Create",
        description = "Schema for creating a new loan"
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class LoanCreateDTO {

    @Schema(
            description = "Mobile number of the customer",
            example = "+2211345678"
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @Schema(
            description = "Type of loan. Enum: PERSONAL, BUSINESS, STUDENT, MORTGAGE, AUTO",
            example = "PERSONAL"
    )
    @NotEmpty(message = "Loan type cannot be null or empty")
    @ValidLoanType
    private String loanType;

}
