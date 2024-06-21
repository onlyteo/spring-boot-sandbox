package com.onlyteo.sandbox.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class SearchResultDto(
    @JsonProperty("accounts") @field:NotNull val accounts: List<AccountDto>,
    @JsonProperty("statuses") @field:NotNull val statuses: List<StatusDto>
)
