package com.onlyteo.sandbox.properties

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class ApplicationProperties(
    @field:NotNull val security: SecurityProperties
)

data class SecurityProperties(@field:NotNull val whitelistedPaths: List<String>)