package com.onlyteo.sandbox.model

import com.onlyteo.sandbox.validator.ValidPassword
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@ValidPassword
data class RegisterFormData(
    @field:Size(min = 3, message = "{validation.username.Size.message}") val username: String? = null,
    @field:Size(min = 6, message = "{validation.password.Size.message}") val password: String? = null,
    @field:NotBlank val confirmPassword: String? = null,
)
