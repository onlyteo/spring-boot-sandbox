package com.onlyteo.sandbox.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class ApplicationProperties(
    @field:Valid @field:NotNull val kafka: KafkaProperties
)

data class KafkaProperties(
    @field:Valid @field:NotNull val consumer: KafkaConsumerProperties
)

data class KafkaConsumerProperties(
    @field:NotBlank val sourceTopic: String
)