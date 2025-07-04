package com.onlyteo.sandbox.app

import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(ApplicationProperties::class)
@SpringBootApplication
class FormLoginFrontendApplication

fun main(args: Array<String>) {
    runApplication<FormLoginFrontendApplication>(*args)
}