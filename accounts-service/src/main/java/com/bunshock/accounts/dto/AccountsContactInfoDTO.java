package com.bunshock.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class AccountsContactInfoDTO {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}
