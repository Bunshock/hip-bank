package com.bunshock.accounts.controller;

import com.bunshock.accounts.dto.AccountsContactInfoDTO;
import com.bunshock.accounts.dto.ResponseErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Information REST APIs for Accounts in Hip Bank",
        description = "Build and Java versions. Contact information."
)
@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountInfoController {

    private final String buildVersion;
    private final Environment environment;
    private final AccountsContactInfoDTO accountsContactInfo;

    @Autowired
    public AccountInfoController(
            @Value("${build.version:unknown}") String buildVersion,
            Environment environment,
            AccountsContactInfoDTO accountsContactInfo) {
        this.buildVersion = buildVersion;
        this.environment = environment;
        this.accountsContactInfo = accountsContactInfo;
    }

    @Operation(
            summary = "Get build information",
            description = "Get build information for the accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Build information fetched successfully",
                                    value = "1.0.0-SNAPSHOT"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/build-info\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return new ResponseEntity<>(buildVersion, HttpStatus.OK);
    }

    @Operation(
            summary = "Get java version",
            description = "Get java version for the accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Java version fetched successfully",
                                    value = "C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.4.7-hotspot\\"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/java-version\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return new ResponseEntity<>(environment.getProperty("JAVA_HOME"), HttpStatus.OK);
    }

    @Operation(
            summary = "Get contact information",
            description = "Get contact information for the accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status: OK",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Contact information fetched successfully",
                                    value = "{\"message\":\"Welcome to HipBank accounts microservice\"," +
                                            "\"contactDetails\":{\"name\":\"Bunshock\",\"email\":" +
                                            "\"bunshock@me.com\"},\"onCallSupport\":[\"(555) 555-1234\"," +
                                            "\"(555) 555-1345\"]}"
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
                                            "\",\"apiPath\":\"uri=/api/accounts/contact-info\",\"" +
                                            "errorMessage\":\"Internal server error\"}"
                            )
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDTO> getContactInfo() {
        return new ResponseEntity<>(accountsContactInfo, HttpStatus.OK);
    }

}
