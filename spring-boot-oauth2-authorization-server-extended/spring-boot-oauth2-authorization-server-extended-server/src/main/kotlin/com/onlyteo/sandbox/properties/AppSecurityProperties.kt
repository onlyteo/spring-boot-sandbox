package com.onlyteo.sandbox.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.security")
data class AppSecurityProperties(
    @field:Valid @field:NotBlank val keys: List<AppKeyProperties>,
    @field:Valid @field:NotBlank val users: List<AppUserProperties>,
    @field:Valid @field:NotBlank val whitelistedPaths: List<String>
)

data class AppUserProperties(
    @NotBlank val username: String,
    @NotBlank val password: String,
    @NotNull val roles: List<String>
)

data class AppKeyProperties(
    @field:NotBlank val keyId: String,
    @field:NotBlank val publicKeyLocation: String,
    @field:NotBlank val privateKeyLocation: String
)