package com.onlyteo.sandbox.app

import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(ApplicationProperties::class)
@SpringBootApplication
class RestApiBackendApplication

fun main(args: Array<String>) {
    runApplication<RestApiBackendApplication>(*args)
}