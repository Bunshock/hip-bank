package com.bunshock.accounts.controller;

import com.bunshock.accounts.constants.AccountConstants;
import com.bunshock.accounts.dto.ResponseErrorDTO;
import com.bunshock.accounts.dto.customer.CustomerAccountDetailsDTO;
import com.bunshock.accounts.dto.customer.CustomerAccountUpdateDTO;
import com.bunshock.accounts.dto.customer.CustomerInputDTO;
import com.bunshock.accounts.dto.ResponseDTO;
import com.bunshock.accounts.dto.ResponseSuccessDTO;
import com.bunshock.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for Accounts in Hip Bank",
        description = "CREATE, READ, UPDATE and DELETE account details"
)
@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountController {

    private final IAccountService accountService;

    @Operation(
            summary = "Create a customer's account",
            description = "REST API to create a Customer with an associated Account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status: CREATED",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Account created successfully",
                                    value = "{\"statusCode\":201,\"timestamp\":\"02-06-2025 23:03" +
                                            "\",\"message\":\"Account created successfully\"}"
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
                                    value = "{\"statusCode\":400,\"timestamp\":\"03-06-2025 01:05" +
                                            "\",\"apiPath\":\"uri=/api/accounts/create\",\"errors" +
                                            "\":{\"mobileNumber\":\"Invalid mobile number format\"," +
                                            "\"name\":\"Name cannot be null or empty\",\"email\":\"" +
                                            "Invalid email address format\"}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "HTTP Status: CONFLICT. Possible cause: specified mobile" +
                            " number already exists. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Mobile number already exists",
                                    value = "{\"statusCode\":409,\"timestamp\":\"02-06-2025 23:03" +
                                            "\",\"apiPath\":\"uri=/api/accounts/create\",\"" +
                                            "errorMessage\":\"Customer with mobile number " +
                                            "'+254712345678' already exists\"}"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/create\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccounts(
            @Valid @RequestBody CustomerInputDTO customerInput
    ) {
        accountService.createAccount(customerInput);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_201, "Account"))
                .build(), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Fetch all customer's account details",
            description = "REST API to fetch every Customer's account details," +
                    " including a Customer with an associated Account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Accounts fetched successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"03-06-2025 01:14\"" +
                                            ",\"message\":\"Successful operation: Fetched all account" +
                                            " details\",\"data\":[{\"name\":\"Bunshock\",\"email\":\"" +
                                            "bunshock@me.com\",\"mobileNumber\":\"+254712345678\",\"" +
                                            "account\":{\"accountNumber\":\"1162954287\",\"accountType" +
                                            "\":\"SAVINGS\",\"branchAddress\":\"1234 Main Street, Anytown" +
                                            ", USA\"}},{\"name\":\"Bunshock Copycat\",\"email\":\"bunshock" +
                                            "@notme.com\",\"mobileNumber\":\"+154712345678\",\"account\":{" +
                                            "\"accountNumber\":\"9933458606\",\"accountType\":\"SAVINGS\"," +
                                            "\"branchAddress\":\"1234 Main Street, Anytown, USA\"}}]}"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/fetch\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<ResponseDTO> fetchAccounts() {
        return new ResponseEntity<>(ResponseSuccessDTO.<List<CustomerAccountDetailsDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Fetched all account details"))
                .data(accountService.fetchAllAccountDetails())
                .build(), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch a single customer's account details",
            description = "REST API to fetch a Customer's account details," +
                    " including a Customer with an associated Account. NOTE: " +
                    "the mobile number parameter must be encoded in the URL."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Account fetched successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"03-06-2025 01:25\"," +
                                            "\"message\":\"Successful operation: Fetched account " +
                                            "details for customer with mobile number: +254712345678\"," +
                                            "\"data\":{\"name\":\"Bunshock\",\"email\":\"bunshock@me.com" +
                                            "\",\"mobileNumber\":\"+254712345678\",\"account\":{" +
                                            "\"accountNumber\":\"2977614077\",\"accountType\":\"SAVINGS\"," +
                                            "\"branchAddress\":\"1234 Main Street, Anytown, USA\"}}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified mobile" +
                            " number does not match any existing customer. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Customer not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"03-06-2025 01:20\"," +
                                            "\"apiPath\":\"uri=/api/accounts/fetch/%2B1%20126%202331112" +
                                            "\",\"errorMessage\":\"Customer not found with mobileNumber " +
                                            ": '+1 126 2331112'\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status: BAD REQUEST. Possible cause: invalid " +
                            "mobile number format. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Invalid mobile number format",
                                    value = "{\"statusCode\":500,\"timestamp\":\"03-06-2025 01:26\"," +
                                            "\"apiPath\":\"uri=/api/accounts/fetch/invalidNumber\"," +
                                            "\"errorMessage\":\"fetchAccountDetails.mobileNumber: " +
                                            "Invalid mobile number format\"}"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/fetch/%2B1%20126%202331112\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @GetMapping("/fetch/{mobileNumber}")
    public ResponseEntity<ResponseDTO> fetchAccountDetails(
            @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
                    "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
            @PathVariable String mobileNumber
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<CustomerAccountDetailsDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Fetched account details " +
                        "for customer with mobile number: " + mobileNumber))
                .data(accountService.fetchAccountDetails(mobileNumber))
                .build(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update a customer's account details",
            description = "REST API to update a Customer's account details. NOTE: " +
                    "the mobile number parameter must be encoded in the URL."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Account updated successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"03-06-2025 01:37\"," +
                                            "\"message\":\"Successful operation: Updated account " +
                                            "details for customer with mobile number: +254712345678\"," +
                                            "\"data\":{\"name\":\"Carl\",\"email\":\"bunshock@me.com\"," +
                                            "\"mobileNumber\":\"+254712345678\",\"account\":{" +
                                            "\"accountNumber\":\"5831960233\",\"accountType\":\"SAVINGS" +
                                            "\",\"branchAddress\":\"1455 My House St., New York, USA\"}}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified mobile" +
                            " number does not match any existing customer. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Customer not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"03-06-2025 01:35\"," +
                                            "\"apiPath\":\"uri=/api/accounts/update/%2B1%20126%202331112\"," +
                                            "\"errorMessage\":\"Customer not found with mobileNumber :" +
                                            " '+1 126 2331112'\"}"
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
                                    value = "{\"statusCode\":400,\"timestamp\":\"03-06-2025 01:41\"," +
                                            "\"apiPath\":\"uri=/api/accounts/update/%2B254712345678\"," +
                                            "\"errors\":{\"name\":\"Name must be between 3 and 50 " +
                                            "characters\",\"email\":\"Invalid email address format\"}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "HTTP Status: CONFLICT. Possible cause: specified updated " +
                            "mobile number already exists. Check 'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Mobile number already exists",
                                    value = "{\"statusCode\":409,\"timestamp\":\"03-06-2025 01:45\"," +
                                            "\"apiPath\":\"uri=/api/accounts/update/%2B254712345678\"," +
                                            "\"errorMessage\":\"Customer with mobile number '+254712345678'" +
                                            " already exists\"}"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/update/%2B1%20126%202331112\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @PutMapping("/update/{mobileNumber}")
    public ResponseEntity<ResponseDTO> updateCustomerAccount(
            @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
                    "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
            @PathVariable String mobileNumber,
            @Valid @RequestBody CustomerAccountUpdateDTO updatedAccount
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<CustomerAccountDetailsDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Updated account details " +
                        "for customer with mobile number: " + mobileNumber))
                .data(accountService.updateAccount(mobileNumber, updatedAccount))
                .build(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a customer's account details",
            description = "REST API to delete a Customer's account details. NOTE: " +
                    "the mobile number parameter must be encoded in the URL."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Account deleted successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"03-06-2025 01:48\"," +
                                            "\"message\":\"Successful operation: Deleted account " +
                                            "details for customer with mobile number: +254712345678\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified mobile" +
                            " number does not match any existing customer. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Mobile number not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"03-06-2025 01:49\"," +
                                            "\"apiPath\":\"uri=/api/accounts/delete/%2B955688856781\"," +
                                            "\"errorMessage\":\"Customer not found with mobileNumber" +
                                            " : '+955688856781'\"}"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/delete/%2B1%20126%202331112\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @DeleteMapping("/delete/{mobileNumber}")
    @Transactional
    public ResponseEntity<ResponseDTO> deleteCustomerAccount(
            @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]" +
                    "?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid mobile number format")
            @PathVariable String mobileNumber
    ) {
        accountService.deleteAccount(mobileNumber);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(AccountConstants.MESSAGE_200, "Deleted account details " +
                        "for customer with mobile number: " + mobileNumber))
                .build(), HttpStatus.OK);
    }

}
