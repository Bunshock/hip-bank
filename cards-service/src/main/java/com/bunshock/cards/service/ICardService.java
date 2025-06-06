package com.bunshock.cards.service;

import com.bunshock.cards.dto.card.CardCreateDTO;
import com.bunshock.cards.dto.card.CardShowDTO;
import com.bunshock.cards.dto.card.CardUpdateDTO;

import java.util.List;

public interface ICardService {

    /**
     * Create a new card.
     *
     * @param cardInput the information of the card to be created
     */
    void createCard(CardCreateDTO cardInput);


    /**
     * Fetch all the card details.
     *
     * @return list of cards
     */
    List<CardShowDTO> fetchAllCards();

    /**
     * Fetch a specific card's details using its card number.
     *
     * @param cardNumber the unique number of the card to be fetched
     * @return the details of the card
     */
    CardShowDTO fetchCard(String cardNumber);

    /**
     * Update the details of an existing card.
     *
     * @param cardNumber the unique number of the card to be updated
     * @param updatedCard the new details to update the card with
     * @return the updated details of the card
     */
    CardShowDTO updateCard(String cardNumber, CardUpdateDTO updatedCard);


    /**
     * Delete a card using its card number.
     *
     * @param cardNumber the unique number of the card to be deleted
     */
    void deleteCard(String cardNumber);

    /**
     * Spend a specified amount of money from a card's available balance.
     *
     * @param cardNumber the unique number of the card from which money will be spent
     * @param amount the amount of money to spend
     */
    void spendMoney(String cardNumber, Double amount);

    // TODO: Add a method to get all cards for a specific customer

}
