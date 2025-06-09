package com.onlyteo.sandbox.app.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PersonFormData(
    @field:NotBlank val name: String,
    @field:NotNull val history: String
)
