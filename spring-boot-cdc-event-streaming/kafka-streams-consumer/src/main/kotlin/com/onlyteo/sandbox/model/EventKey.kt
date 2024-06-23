package com.onlyteo.sandbox.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class EventKey(@JsonProperty("id") @field:NotBlank val id: String)
