package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GreetingService {

    private val logger: Logger = LoggerFactory.getLogger(GreetingService::class.java)

    fun getGreeting(person: Person): Greeting {
        logger.info("Returning greeting to {}", person.name)
        return person.toGreeting()
    }
}