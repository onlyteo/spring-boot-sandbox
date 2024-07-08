package com.onlyteo.sandbox

import com.onlyteo.sandbox.properties.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(ApplicationProperties::class)
@SpringBootApplication
class OAuth2ClientReactFrontendApiApplication

fun main(args: Array<String>) {
    runApplication<OAuth2ClientReactFrontendApiApplication>(*args)
}