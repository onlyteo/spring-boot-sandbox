package com.onlyteo.sandbox.app.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "app")
data class ApplicationProperties(
    @field:Valid @field:NotNull val resources: ResourcesProperties,
    @field:Valid @field:NotNull val kafka: KafkaProperties
)

data class ResourcesProperties(
    @field:NotBlank val prefixesFile: String
)

data class KafkaProperties(
    @field:Valid @field:NotNull val streams: KafkaStreamsProperties
)

data class KafkaStreamsProperties(
    @field:NotBlank val sourceTopic: String,
    @field:NotBlank val sinkTopic: String,
    @field:NotBlank val stateStore: String,
    @field:NotBlank val processor: String,
    @field:NotNull val punctuatorSchedule: Duration,
    @field:NotNull val processingDelay: Duration
)