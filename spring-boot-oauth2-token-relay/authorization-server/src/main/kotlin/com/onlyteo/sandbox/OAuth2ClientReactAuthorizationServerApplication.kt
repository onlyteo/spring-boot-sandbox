package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuth2ClientReactAuthorizationServerApplication

fun main(args: Array<String>) {
    runApplication<OAuth2ClientReactAuthorizationServerApplication>(*args)
}