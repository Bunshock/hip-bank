package com.bunshock.accounts.dto;

import java.util.List;
import java.util.Map;

public record AccountsContactInfoDTO(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}
