package com.onlyteo.sandbox.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URL

@ConfigurationProperties(prefix = "app.rest")
data class AppRestProperties(
    @field:Valid @field:NotNull val consumer: AppConsumerProperties
)

data class AppConsumerProperties(
    @field:Valid @field:NotNull val mastodon: AppClientProperties
)

data class AppClientProperties(
    @field:NotNull val url: URL,
    @field:NotBlank val bearerToken: String
)