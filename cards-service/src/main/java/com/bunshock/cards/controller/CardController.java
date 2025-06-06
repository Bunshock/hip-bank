package com.bunshock.cards.controller;

import com.bunshock.cards.constants.CardConstants;
import com.bunshock.cards.dto.ResponseDTO;
import com.bunshock.cards.dto.ResponseSuccessDTO;
import com.bunshock.cards.dto.card.CardCreateDTO;
import com.bunshock.cards.dto.card.CardShowDTO;
import com.bunshock.cards.dto.card.CardUpdateDTO;
import com.bunshock.cards.service.ICardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cards", produces = "application/json")
@AllArgsConstructor
@Validated
public class CardController {

    private final ICardService cardService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCard(
            @Valid @RequestBody CardCreateDTO cardInput
    ) {
        cardService.createCard(cardInput);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(CardConstants.MESSAGE_201, "Card"))
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ResponseDTO> fetchAllCards() {
        return new ResponseEntity<>(ResponseSuccessDTO.<List<CardShowDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(CardConstants.MESSAGE_200, "Fetched all card details"))
                .data(cardService.fetchAllCards())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/fetch/{cardNumber}")
    public ResponseEntity<ResponseDTO> fetchCard(
            @Pattern(regexp = "^\\d{10}$", message = "Card number must be exactly 10 digits")
            @PathVariable String cardNumber
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<CardShowDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(CardConstants.MESSAGE_200, "Fetched card details for card number "
                        + cardNumber))
                .data(cardService.fetchCard(cardNumber))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update/{cardNumber}")
    public ResponseEntity<ResponseDTO> updateCard(
            @Pattern(regexp = "^\\d{10}$", message = "Card number must be exactly 10 digits")
            @PathVariable String cardNumber,
            @Valid @RequestBody CardUpdateDTO updatedCard
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<CardShowDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(CardConstants.MESSAGE_200, "Updated card details for card number "
                        + cardNumber))
                .data(cardService.updateCard(cardNumber, updatedCard))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cardNumber}")
    public ResponseEntity<ResponseDTO> deleteCard(
            @Pattern(regexp = "^\\d{10}$", message = "Card number must be exactly 10 digits")
            @PathVariable String cardNumber
    ) {
        cardService.deleteCard(cardNumber);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(CardConstants.MESSAGE_200, "Deleted card details for card number "
                        + cardNumber))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/spend/{cardNumber}")
    public ResponseEntity<ResponseDTO> spendMoney(
            @Pattern(regexp = "^\\d{10}$", message = "Card number must be exactly 10 digits")
            @PathVariable String cardNumber,
            @Positive @RequestParam Double amount
    ) {
        cardService.spendMoney(cardNumber, amount);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(CardConstants.MESSAGE_200, "Money spent successfully for card number "
                        + cardNumber + " (amount: " + amount + ")"))
                .build(), HttpStatus.OK);
    }

}
