package com.onlyteo.sandbox.model;

import com.onlyteo.sandbox.validator.ValidPassword;
import jakarta.validation.constraints.Size;

@ValidPassword
public record RegisterFormData(
        @Size(min = 3, message = "{validation.username.Size.message}")
        String username,
        @Size(min = 6, message = "{validation.password.Size.message}")
        String password,
        String confirmPassword) {
}
