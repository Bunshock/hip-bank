package com.bunshock.accounts.dto.customer;

import com.bunshock.accounts.dto.account.AccountUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Account details",
        description = "Schema for updating a customer's account details"
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class CustomerAccountUpdateDTO {

    @Schema(
            description = "Name of the customer",
            example = "Bunshock"
    )
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Schema(
            description = "Email address of the customer",
            example = "bunshock@me.com"
    )
    @Email(message = "Invalid email address format")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "+254712345678"
    )
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @Schema(
            description = "Account details"
    )
    private AccountUpdateDTO account;

}
