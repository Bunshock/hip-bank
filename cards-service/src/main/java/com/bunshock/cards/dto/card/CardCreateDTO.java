package com.bunshock.cards.dto.card;

import com.bunshock.cards.validation.ValidCardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Card: Create",
        description = "Schema for creating a new card"
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class CardCreateDTO {

    @Schema(
            description = "Mobile number of the customer",
            example = "+11223345678"
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @Schema(
            description = "Type of card. Enum: DEBIT, CREDIT",
            example = "CREDIT"
    )
    @NotEmpty(message = "Card type cannot be null or empty")
    @ValidCardType
    private String cardType;

}
