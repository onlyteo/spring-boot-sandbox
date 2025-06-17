package com.onlyteo.sandbox.app.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class ApplicationProperties(
    @field:Valid @field:NotNull val resources: ResourcesProperties,
    @field:Valid @field:NotNull val security: SecurityProperties
)

data class ResourcesProperties(@field:NotBlank val prefixesFile: String)

data class SecurityProperties(
    @field:NotNull val users: List<UserProperties>
)

data class UserProperties(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String,
    @field:NotNull val roles: List<String>
)