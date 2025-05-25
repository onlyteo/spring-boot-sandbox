package com.onlyteo.sandbox.app.model

import com.onlyteo.sandbox.app.validator.PasswordsMatch
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@PasswordsMatch
data class RegisterFormData(
    @field:Size(min = 3, message = "{validation.username.Size.message}") val username: String? = null,
    @field:Size(min = 6, message = "{validation.password.Size.message}") val password: String? = null,
    @field:NotBlank(message = "{validation.confirmPassword.NotBlank.message}") val confirmPassword: String? = null,
)
