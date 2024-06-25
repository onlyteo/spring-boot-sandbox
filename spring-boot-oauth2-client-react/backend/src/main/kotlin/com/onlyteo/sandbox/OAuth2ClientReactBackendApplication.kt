package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuth2ClientReactBackendApplication

fun main(args: Array<String>) {
    runApplication<OAuth2ClientReactBackendApplication>(*args)
}