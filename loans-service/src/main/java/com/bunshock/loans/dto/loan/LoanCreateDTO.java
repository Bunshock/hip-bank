package com.bunshock.loans.dto.loan;

import com.bunshock.loans.validation.ValidLoanType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class LoanCreateDTO {

    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @NotEmpty(message = "Loan type cannot be null or empty")
    @ValidLoanType
    private String loanType;

}
