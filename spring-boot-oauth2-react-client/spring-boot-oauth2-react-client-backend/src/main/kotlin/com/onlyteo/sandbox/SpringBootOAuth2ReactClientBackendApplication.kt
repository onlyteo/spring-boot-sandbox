package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootOAuth2ReactClientBackendApplication

fun main(args: Array<String>) {
    runApplication<SpringBootOAuth2ReactClientBackendApplication>(*args)
}