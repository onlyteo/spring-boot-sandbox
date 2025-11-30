package com.onlyteo.sandbox.app.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class CdcKey(
    @field:JsonProperty("id")
    @field:NotBlank val id: String
)
