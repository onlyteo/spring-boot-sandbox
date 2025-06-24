package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.app.config.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.repository.PrefixRepository
import org.springframework.stereotype.Service

@Service
class GreetingService(
    private val prefixRepository: PrefixRepository
) {
    private val logger = buildLogger

    fun getGreeting(name: String): Greeting {
        val prefix = prefixRepository.getPrefix()
        val message = "${prefix.greeting} ${name}!"
        logger.info("Returning greeting to \"{}\"", name)
        return Greeting(message)
    }
}