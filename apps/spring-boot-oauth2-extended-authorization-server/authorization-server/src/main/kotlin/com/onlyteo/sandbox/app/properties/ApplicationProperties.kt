package com.onlyteo.sandbox.app.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class ApplicationProperties(
    @field:Valid @field:NotNull val security: SecurityProperties
)

data class SecurityProperties(
    @field:Valid @field:NotBlank val keys: List<KeyProperties>,
    @field:Valid @field:NotBlank val users: List<UserProperties>,
    @field:Valid @field:NotBlank val whitelistedPaths: List<String>
)

data class KeyProperties(
    @field:NotBlank val keyId: String,
    @field:NotBlank val publicKeyLocation: String,
    @field:NotBlank val privateKeyLocation: String
)

data class UserProperties(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String,
    @field:NotNull val roles: List<String>
)
