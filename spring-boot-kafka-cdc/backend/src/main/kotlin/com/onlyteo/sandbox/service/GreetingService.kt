package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.repository.PrefixRepository
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