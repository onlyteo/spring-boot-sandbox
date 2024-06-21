package com.onlyteo.sandbox.properties

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.security")
data class AppSecurityProperties(@field:NotNull val whitelistedPaths: List<String>)