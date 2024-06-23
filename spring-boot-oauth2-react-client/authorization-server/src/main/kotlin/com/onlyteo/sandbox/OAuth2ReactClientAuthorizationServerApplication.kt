package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuth2ReactClientAuthorizationServerApplication

fun main(args: Array<String>) {
    runApplication<OAuth2ReactClientAuthorizationServerApplication>(*args)
}