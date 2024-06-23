package com.onlyteo.sandbox.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.security")
data class AppSecurityProperties(val whitelistedPaths: List<String>)
