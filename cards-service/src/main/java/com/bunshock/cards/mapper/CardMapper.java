package com.bunshock.cards.mapper;

import com.bunshock.cards.dto.card.CardShowDTO;
import com.bunshock.cards.entity.Card;

public class CardMapper {

    public static CardShowDTO mapToCardShowDTO(Card card) {
        return CardShowDTO.builder()
                .mobileNumber(card.getMobileNumber())
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .cardLimit(card.getCardLimit())
                .amountUsed(card.getAmountUsed())
                .availableAmount(card.getAvailableAmount())
                .build();
    }

}
