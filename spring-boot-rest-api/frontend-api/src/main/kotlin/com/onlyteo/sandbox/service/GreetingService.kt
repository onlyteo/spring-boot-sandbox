package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.server.ResponseStatusException

@Service
class GreetingService(private val restClient: RestClient) {

    private val logger: Logger = LoggerFactory.getLogger(GreetingService::class.java)

    fun getGreeting(person: Person): Greeting {
        logger.info("Fetching greeting for {}", person.name)
        return restClient.post()
            .uri("/api/greetings")
            .body(person)
            .retrieve()
            .body<Greeting>() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Could not fetch greeting")
    }
}