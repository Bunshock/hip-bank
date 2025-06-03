package com.bunshock.accounts.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Customer",
        description = "Schema for creating a new customer"
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CustomerInputDTO {

    @Schema(
            description = "Name of the customer",
            example = "Bunshock"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Schema(
            description = "Email address of the customer",
            example = "bunshock@me.com"
    )
    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Invalid email address format")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "+254712345678"
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

}
