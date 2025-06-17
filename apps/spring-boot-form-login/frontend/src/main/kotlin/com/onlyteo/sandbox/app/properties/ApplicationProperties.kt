package com.onlyteo.sandbox.app.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URL

@ConfigurationProperties(prefix = "app")
data class ApplicationProperties(
    @field:Valid @field:NotNull val integrations: IntegrationsProperties,
    @field:Valid @field:NotNull val security: SecurityProperties
)

data class IntegrationsProperties(
    @field:Valid @field:NotNull val backend: IntegrationProperties
)

data class IntegrationProperties(
    @field:NotNull val url: URL,
    @field:NotBlank val username: String,
    @field:NotBlank val password: String
)

data class SecurityProperties(
    @field:NotNull val users: List<UserProperties>
)

data class UserProperties(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String,
    @field:NotNull val roles: List<String>
)