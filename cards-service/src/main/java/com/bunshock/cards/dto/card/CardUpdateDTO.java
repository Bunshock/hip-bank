package com.bunshock.cards.dto.card;

import com.bunshock.cards.validation.ValidCardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Card: Update",
        description = "Schema for updating a card"
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class CardUpdateDTO {

    @Schema(
            description = "Mobile number of the customer",
            example = "+11223345678"
    )
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
            "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @Schema(
            description = "Type of card. Enum: DEBIT, CREDIT",
            example = "CREDIT"
    )
    @ValidCardType
    private String cardType;

    @Schema(
            description = "Card limit",
            example = "10000.0"
    )
    @Positive
    private Double cardLimit;

}
