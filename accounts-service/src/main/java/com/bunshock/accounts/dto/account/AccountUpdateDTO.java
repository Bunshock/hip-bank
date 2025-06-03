package com.bunshock.accounts.dto.account;

import com.bunshock.accounts.validation.ValidAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Account",
        description = "Schema for updating a customer's account details"
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class AccountUpdateDTO {

    // Keep accountType as String to allow for custom validation
    @Schema(
            description = "Type of the account",
            example = "SAVINGS"
    )
    @ValidAccountType
    private String accountType;

    // TODO: Add validation
    @Schema(
            description = "Branch address of the account",
            example = "123 Main Street, New York, USA"
    )
    private String branchAddress;

}
