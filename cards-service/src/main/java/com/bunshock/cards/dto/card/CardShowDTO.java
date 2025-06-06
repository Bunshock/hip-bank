package com.bunshock.cards.dto.card;

import com.bunshock.cards.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class CardShowDTO {

    private String mobileNumber;
    private String cardNumber;
    private CardType cardType;
    private Double cardLimit;
    private Double amountUsed;
    private Double availableAmount;

}
