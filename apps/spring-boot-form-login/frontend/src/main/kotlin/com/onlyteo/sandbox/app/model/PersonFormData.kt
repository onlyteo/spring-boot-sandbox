package com.onlyteo.sandbox.app.model

import jakarta.validation.constraints.NotBlank

data class PersonFormData(
    @field:NotBlank val name: String
)
