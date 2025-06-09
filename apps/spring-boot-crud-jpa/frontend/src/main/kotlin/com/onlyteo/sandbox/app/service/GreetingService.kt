package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
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

    fun getGreeting(name: String): Greeting {
        logger.info("Fetching greeting for {}", name)
        return restClient.post()
            .uri("/api/greetings")
            .body(Person(name))
            .retrieve()
            .body<Greeting>() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Could not fetch greeting")
    }

    fun findGreetings(name: String): List<Greeting> {
        logger.info("Fetching greetings for \"{}\"", name)
        return restClient.get()
            .uri("/api/greetings?name=$name")
            .retrieve()
            .body<List<Greeting>>() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Could not fetch greetings")
    }
}