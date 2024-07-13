package com.onlyteo.sandbox.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.kafka")
data class AppKafkaProperties(@field:Valid @field:NotNull val streams: AppKafkaStreamsProperties)

data class AppKafkaStreamsProperties(
    @field:NotBlank val accountSourceTopic: String,
    @field:NotBlank val statusSourceTopic: String
)
