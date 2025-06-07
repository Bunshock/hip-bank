package com.bunshock.cards.service.impl;

import com.bunshock.cards.constants.CardConstants;
import com.bunshock.cards.dto.card.CardCreateDTO;
import com.bunshock.cards.dto.card.CardShowDTO;
import com.bunshock.cards.dto.card.CardUpdateDTO;
import com.bunshock.cards.entity.Card;
import com.bunshock.cards.enums.CardType;
import com.bunshock.cards.exception.IdGenerationException;
import com.bunshock.cards.exception.InsufficientFundsException;
import com.bunshock.cards.exception.ResourceNotFoundException;
import com.bunshock.cards.mapper.CardMapper;
import com.bunshock.cards.repository.ICardRepository;
import com.bunshock.cards.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService implements ICardService {

    private final ICardRepository cardRepository;
    private final CardNumberGeneratorService cardNumberGenerator;

    /**
     * Generates a unique card number by attempting a number of times to produce
     * a new card number that doesn't exist in the repository.
     *
     * @return a unique card number as a String
     * @throws IdGenerationException if unable to generate a unique card number
     *         after the specified number of attempts
     */
    private String generateUniqueCardNumber() {
        int maxAttempts = 10;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            String newCardNumber = cardNumberGenerator.generateId();
            if (!cardRepository.existsByCardNumber(newCardNumber)) {
                return newCardNumber;
            }
        }
        throw new IdGenerationException("Failed to generate a unique card" +
                " number after " + maxAttempts + " attempts");
    }


    @Override
    public void createCard(CardCreateDTO cardInput) {
        Card card = Card.builder()
                .mobileNumber(cardInput.getMobileNumber())
                .cardNumber(generateUniqueCardNumber())
                .cardType(CardType.valueOf(cardInput.getCardType()))
                .cardLimit(CardConstants.NEW_CARD_LIMIT)
                .amountUsed(0.0)
                .availableAmount(CardConstants.NEW_CARD_LIMIT)
                .build();
        cardRepository.save(card);
    }

    @Override
    public List<CardShowDTO> fetchAllCards() {
        return cardRepository.findAll().stream()
                .map(CardMapper::mapToCardShowDTO)
                .toList();
    }

    @Override
    public CardShowDTO fetchCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardNumber));
        return CardMapper.mapToCardShowDTO(card);
    }

    @Override
    public CardShowDTO updateCard(String cardNumber, CardUpdateDTO updatedCard) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardNumber));

        if (updatedCard.getMobileNumber() != null)
            card.setMobileNumber(updatedCard.getMobileNumber());
        if (updatedCard.getCardType() != null)
            card.setCardType(CardType.valueOf(updatedCard.getCardType()));
        if (updatedCard.getCardLimit() != null) {
            card.setCardLimit(updatedCard.getCardLimit());
            card.setAvailableAmount(updatedCard.getCardLimit() - card.getAmountUsed());
        }

        return CardMapper.mapToCardShowDTO(cardRepository.save(card));
    }

    @Override
    public void deleteCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardNumber));
        cardRepository.delete(card);
    }

    @Override
    public void spendMoney(String cardNumber, Double amount) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardNumber));

        if (amount > card.getAvailableAmount())
            throw new InsufficientFundsException("Insufficient funds in card with card number "
                    + cardNumber);

        card.setAvailableAmount(card.getAvailableAmount() - amount);
        card.setAmountUsed(card.getAmountUsed() + amount);
        cardRepository.save(card);
    }

}
