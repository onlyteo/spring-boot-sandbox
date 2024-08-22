package com.onlyteo.sandbox

import com.onlyteo.sandbox.properties.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(ApplicationProperties::class)
@SpringBootApplication
class OtelObservabilityBackendApplication

fun main(args: Array<String>) {
    runApplication<OtelObservabilityBackendApplication>(*args)
}