package com.onlyteo.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SearchResultDto(@JsonProperty("accounts") @NotNull List<AccountDto> accounts,
                              @JsonProperty("statuses") @NotNull List<StatusDto> statuses) {
}
