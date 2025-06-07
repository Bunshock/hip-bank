package com.bunshock.cards.controller;

import com.bunshock.cards.constants.CardConstants;
import com.bunshock.cards.dto.ResponseDTO;
import com.bunshock.cards.dto.ResponseErrorDTO;
import com.bunshock.cards.dto.ResponseSuccessDTO;
import com.bunshock.cards.dto.card.CardCreateDTO;
import com.bunshock.cards.dto.card.CardShowDTO;
import com.bunshock.cards.dto.card.CardUpdateDTO;
import com.bunshock.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "CRUD REST APIs for Cards in Hip Bank",
        description = "CREATE, READ, UPDATE and DELETE card details"
)
@RestController
@RequestMapping(path = "/api/cards", produces = "application/json")
@AllArgsConstructor
@Validated
public class CardController {

    private final ICardService cardService;

    @Operation(
            summary = "Create a new card",
            description = "REST API to create a card for a customer with an associated mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status: CREATED",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Card created successfully",
                                    value = "{\"statusCode\":201,\"timestamp\":\"06-06-2025 16:37\"," +
                                            "\"message\":\"Card created successfully\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status: BAD REQUEST. Possible cause: invalid " +
                            "input data. Check 'errors' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Invalid input data",
                                    value = "{\"statusCode\":400,\"timestamp\":\"06-06-2025 16:49\"," +
                                            "\"apiPath\":\"uri=/api/cards/create\",\"errors\":{" +
                                            "\"mobileNumber\":\"Invalid mobile number format\"," +
                                            "\"cardType\":\"Invalid card type\"}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status: INTERNAL SERVER ERROR. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Internal server error",
                                    value = "{\"statusCode\":500,\"timestamp\":\"02-06-2025 23:03" +
                                            "\",\"apiPath\":\"uri=/api/cards/create\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
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

    @Operation(
            summary = "Fetch all cards",
            description = "REST API to fetch every card details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Cards fetched successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"06-06-2025 16:53\"," +
                                            "\"message\":\"Successful operation: Fetched all card " +
                                            "details\",\"data\":[{\"mobileNumber\":\"+91243558671\"," +
                                            "\"cardNumber\":\"7873355014\",\"cardType\":\"DEBIT\"," +
                                            "\"cardLimit\":150000,\"amountUsed\":0,\"availableAmount" +
                                            "\":150000},{\"mobileNumber\":\"+11223345678\"," +
                                            "\"cardNumber\":\"9736285880\",\"cardType\":\"CREDIT\"," +
                                            "\"cardLimit\":100000,\"amountUsed\":0,\"availableAmount\":100000}]}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status: INTERNAL SERVER ERROR. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Internal server error",
                                    value = "{\"statusCode\":500,\"timestamp\":\"02-06-2025 23:03" +
                                            "\",\"apiPath\":\"uri=/api/cards/fetch\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<ResponseDTO> fetchAllCards() {
        return new ResponseEntity<>(ResponseSuccessDTO.<List<CardShowDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(CardConstants.MESSAGE_200, "Fetched all card details"))
                .data(cardService.fetchAllCards())
                .build(), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch a single card details",
            description = "REST API to fetch a card's details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Card fetched successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"07-06-2025 14:47\"," +
                                            "\"message\":\"Successful operation: Fetched card details" +
                                            " for card number 5140213096\",\"data\":{\"mobileNumber\":" +
                                            "\"+11223345678\",\"cardNumber\":\"5140213096\",\"cardType" +
                                            "\":\"CREDIT\",\"cardLimit\":100000,\"amountUsed\":0," +
                                            "\"availableAmount\":100000}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified card" +
                            " number does not match any existing card. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Card not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"07-06-2025 14:48\"," +
                                            "\"apiPath\":\"uri=/api/cards/fetch/1234567890\"," +
                                            "\"errorMessage\":\"Card not found with cardNumber : '1234567890'\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status: INTERNAL SERVER ERROR. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Internal server error: Invalid card number format",
                                    value = "{\"statusCode\":500,\"timestamp\":\"05-06-2025 23:25\"," +
                                            "\"apiPath\":\"uri=/api/cards/fetch/123\",\"errorMessage" +
                                            "\":\"fetchCard.cardNumber: Card number must be exactly 10 digits\"}"
                            )
                    )
            )
    })
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

    @Operation(
            summary = "Update a card",
            description = "REST API to update a card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Card updated successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"07-06-2025 14:50\"," +
                                            "\"message\":\"Successful operation: Updated card details" +
                                            " for card number 5140213096\",\"data\":{\"mobileNumber\":" +
                                            "\"+11223345678\",\"cardNumber\":\"5140213096\",\"cardType" +
                                            "\":\"DEBIT\",\"cardLimit\":250000,\"amountUsed\":0," +
                                            "\"availableAmount\":250000}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified card" +
                            " number does not match any existing card. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Card not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"07-06-2025 14:51\"," +
                                            "\"apiPath\":\"uri=/api/cards/update/1234567890\"," +
                                            "\"errorMessage\":\"Card not found with cardNumber : '1234567890'\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status: BAD REQUEST. Possible cause: invalid " +
                            "input data. Check 'errors' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Invalid input data",
                                    value = "{\"statusCode\":400,\"timestamp\":\"07-06-2025 14:52\"," +
                                            "\"apiPath\":\"uri=/api/cards/update/5140213096\"," +
                                            "\"errors\":{\"mobileNumber\":\"Invalid mobile number format" +
                                            "\",\"cardType\":\"Invalid card type\",\"cardLimit\":" +
                                            "\"must be greater than 0\"}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status: INTERNAL SERVER ERROR. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Internal server error",
                                    value = "{\"statusCode\":500,\"timestamp\":\"02-06-2025 23:03" +
                                            "\",\"apiPath\":\"uri=/api/cards/update/5140213096\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
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

    @Operation(
            summary = "Delete a card",
            description = "REST API to delete a card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Card deleted successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"07-06-2025 14:54\"," +
                                            "\"message\":\"Successful operation: Deleted card details" +
                                            " for card number 5140213096\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified card" +
                            " number does not match any existing card. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Card not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"07-06-2025 14:56\"," +
                                            "\"apiPath\":\"uri=/api/cards/delete/1234567890\"," +
                                            "\"errorMessage\":\"Card not found with cardNumber : '1234567890'\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status: INTERNAL SERVER ERROR. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Internal server error: Invalid loan number format",
                                    value = "{\"statusCode\":500,\"timestamp\":\"07-06-2025 14:57\"," +
                                            "\"apiPath\":\"uri=/api/cards/delete/123\",\"errorMessage" +
                                            "\":\"deleteCard.cardNumber: Card number must be exactly 10 digits\"}"
                            )
                    )
            )
    })
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

    @Operation(
            summary = "Spend money from a card",
            description = "REST API to spend money from a card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Money spent successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"07-06-2025 15:00\"," +
                                            "\"message\":\"Successful operation: Money spent successfully" +
                                            " for card number 4364172931 (amount: 1200.8)\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified card" +
                            " number does not match any existing card. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Card not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"07-06-2025 14:59\"," +
                                            "\"apiPath\":\"uri=/api/cards/spend/5140213096\"," +
                                            "\"errorMessage\":\"Card not found with cardNumber : '5140213096'\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status: INTERNAL SERVER ERROR. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = { @ExampleObject(
                                    name = "Internal server error: Invalid loan number format",
                                    value = "{\"statusCode\":500,\"timestamp\":\"07-06-2025 15:05\"," +
                                            "\"apiPath\":\"uri=/api/cards/spend/123\",\"errorMessage" +
                                            "\":\"spendMoney.cardNumber: Card number must be exactly 10 digits\"}"
                            ), @ExampleObject(
                                    name = "Internal server error: Invalid amount format",
                                    value = "{\"statusCode\":500,\"timestamp\":\"07-06-2025 15:01\"," +
                                            "\"apiPath\":\"uri=/api/cards/spend/4364172931\"," +
                                            "\"errorMessage\":\"spendMoney.amount: must be greater than 0\"}"
                            )}
                    )
            )
    })
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
