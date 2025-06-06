package com.bunshock.loans.controller;

import com.bunshock.loans.constants.LoanConstants;
import com.bunshock.loans.dto.ResponseDTO;
import com.bunshock.loans.dto.ResponseErrorDTO;
import com.bunshock.loans.dto.ResponseSuccessDTO;
import com.bunshock.loans.dto.loan.LoanCreateDTO;
import com.bunshock.loans.dto.loan.LoanShowDTO;
import com.bunshock.loans.dto.loan.LoanUpdateDTO;
import com.bunshock.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        name = "CRUD REST APIs for Loans in Hip Bank",
        description = "CREATE, READ, UPDATE and DELETE loan details"
)
@RestController
@RequestMapping(path = "/api/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class LoanController {

    private final ILoanService loanService;

    @Operation(
            summary = "Create a new loan",
            description = "REST API to create a loan for a customer with an associated mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status: CREATED",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Loan created successfully",
                                    value = "{\"statusCode\":201,\"timestamp\":\"05-06-2025 23:02\"," +
                                            "\"message\":\"Loan created successfully\"}"
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
                                    value = "{\"statusCode\":400,\"timestamp\":\"05-06-2025 23:03\"," +
                                            "\"apiPath\":\"uri=/api/loans/create\",\"errors\":{" +
                                            "\"loanType\":\"Invalid loan type\",\"mobileNumber\":" +
                                            "\"Mobile number cannot be null or empty\"}}"
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
                                            "\",\"apiPath\":\"uri=/api/loans/create\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoan(
            @Valid @RequestBody LoanCreateDTO loanInput
    ) {
        loanService.createLoan(loanInput);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_201, "Loan"))
                .build(), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Fetch all loans",
            description = "REST API to fetch every loan details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Loans fetched successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"05-06-2025 23:18\"," +
                                            "\"message\":\"Successful operation: Fetched all loan " +
                                            "details\",\"data\":[{\"mobileNumber\":\"+2211345678\"," +
                                            "\"loanNumber\":\"8389681893\",\"loanType\":\"PERSONAL\"," +
                                            "\"totalLoan\":20000,\"amountPaid\":0,\"outstandingAmount" +
                                            "\":20000},{\"mobileNumber\":\"+331920123\",\"loanNumber\":" +
                                            "\"6611626309\",\"loanType\":\"BUSINESS\",\"totalLoan\":20000," +
                                            "\"amountPaid\":0,\"outstandingAmount\":20000}]}"
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
                                            "\",\"apiPath\":\"uri=/api/loans/fetch\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<ResponseDTO> fetchAllLoans() {
        return new ResponseEntity<>(ResponseSuccessDTO.<List<LoanShowDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Fetched all loan details"))
                .data(loanService.fetchAllLoans())
                .build(), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch a single loan details",
            description = "REST API to fetch a loan's details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Loan fetched successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"05-06-2025 23:22\"," +
                                            "\"message\":\"Successful operation: Fetched loan details" +
                                            " for loan number 6112860122\",\"data\":{\"mobileNumber\":" +
                                            "\"+331920123\",\"loanNumber\":\"6112860122\",\"loanType\":" +
                                            "\"BUSINESS\",\"totalLoan\":20000,\"amountPaid\":0,\"outstandingAmount" +
                                            "\":20000}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified loan" +
                            " number does not match any existing loan. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Loan not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"05-06-2025 23:26\"," +
                                            "\"apiPath\":\"uri=/api/loans/fetch/1234567890\"," +
                                            "\"errorMessage\":\"Loan not found with loanNumber : '1234567890'\"}"
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
                                    value = "{\"statusCode\":500,\"timestamp\":\"05-06-2025 23:25\"," +
                                            "\"apiPath\":\"uri=/api/loans/fetch/123\",\"errorMessage" +
                                            "\":\"fetchLoan.loanNumber: Loan number must be exactly 10 digits\"}"
                            )
                    )
            )
    })
    @GetMapping("/fetch/{loanNumber}")
    public ResponseEntity<ResponseDTO> fetchLoan(
            @Pattern(regexp = "^\\d{10}$", message = "Loan number must be exactly 10 digits")
            @PathVariable String loanNumber
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<LoanShowDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Fetched loan details for loan number "
                        + loanNumber))
                .data(loanService.fetchLoan(loanNumber))
                .build(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update a loan",
            description = "REST API to update a loan"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Loan updated successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"05-06-2025 23:34\"," +
                                            "\"message\":\"Successful operation: Updated loan details for" +
                                            " loan number 3009103338\",\"data\":{\"mobileNumber\":" +
                                            "\"+331920123\",\"loanNumber\":\"3009103338\",\"loanType\":" +
                                            "\"BUSINESS\",\"totalLoan\":670000,\"amountPaid\":0," +
                                            "\"outstandingAmount\":670000}}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified loan" +
                            " number does not match any existing loan. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Loan not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"05-06-2025 23:30\"," +
                                            "\"apiPath\":\"uri=/api/loans/update/6112860122\"," +
                                            "\"errorMessage\":\"Loan not found with loanNumber : '6112860122'\"}"
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
                                    value = "{\"statusCode\":400,\"timestamp\":\"05-06-2025 23:33\"," +
                                            "\"apiPath\":\"uri=/api/loans/update/6112860122\",\"errors" +
                                            "\":{\"outstandingAmount\":\"must be greater than or equal to 0" +
                                            "\",\"loanType\":\"Invalid loan type\",\"amountPaid\":\"must be" +
                                            " greater than or equal to 0\",\"mobileNumber\":\"Invalid mobile" +
                                            " number format\",\"totalLoan\":\"must be greater than 0\"}}"
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
                                            "\",\"apiPath\":\"uri=/api/loans/update/6112860122\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @PutMapping("/update/{loanNumber}")
    public ResponseEntity<ResponseDTO> updateLoan(
            @Pattern(regexp = "^\\d{10}$", message = "Loan number must be exactly 10 digits")
            @PathVariable String loanNumber,
            @Valid @RequestBody LoanUpdateDTO updatedLoan
    ) {
        return new ResponseEntity<>(ResponseSuccessDTO.<LoanShowDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Updated loan details for loan number "
                        + loanNumber))
                .data(loanService.updateLoan(loanNumber, updatedLoan))
                .build(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a loan",
            description = "REST API to delete a loan"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDTO.class),
                            examples = @ExampleObject(
                                    name = "Loan deleted successfully",
                                    value = "{\"statusCode\":200,\"timestamp\":\"05-06-2025 23:38\"," +
                                            "\"message\":\"Successful operation: Deleted loan details" +
                                            " for loan number 3209489288\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status: NOT FOUND. Possible cause: specified loan" +
                            " number does not match any existing loan. Check " +
                            "'errorMessage' field.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class),
                            examples = @ExampleObject(
                                    name = "Loan not found",
                                    value = "{\"statusCode\":404,\"timestamp\":\"05-06-2025 23:39\"," +
                                            "\"apiPath\":\"uri=/api/loans/delete/1234567890\"," +
                                            "\"errorMessage\":\"Loan not found with loanNumber : '1234567890'\"}"
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
                                    value = "{\"statusCode\":500,\"timestamp\":\"05-06-2025 23:40\"," +
                                            "\"apiPath\":\"uri=/api/loans/delete/123\",\"errorMessage" +
                                            "\":\"deleteLoan.loanNumber: Loan number must be exactly 10 digits\"}"
                            )
                    )
            )
    })
    @DeleteMapping("/delete/{loanNumber}")
    public ResponseEntity<ResponseDTO> deleteLoan(
            @Pattern(regexp = "^\\d{10}$", message = "Loan number must be exactly 10 digits")
            @PathVariable String loanNumber
    ) {
        loanService.deleteLoan(loanNumber);
        return new ResponseEntity<>(ResponseSuccessDTO.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(String.format(LoanConstants.MESSAGE_200, "Deleted loan details for loan number "
                        + loanNumber))
                .build(), HttpStatus.OK);
    }

}
