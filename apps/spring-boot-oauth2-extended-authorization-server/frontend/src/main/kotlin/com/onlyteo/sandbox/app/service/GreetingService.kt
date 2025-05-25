package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.app.config.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class GreetingService(
    private val properties: ApplicationProperties,
    private val backendClient: RestClient
) {

    private val logger = buildLogger

    fun getGreeting(
        authorizedClient: OAuth2AuthorizedClient,
        person: Person
    ): Greeting {
        logger.info("Fetching greeting for \"{}\"", person.name)
        val url = "${properties.integrations.backend.url}/api/greetings"
        return backendClient.post()
            .uri(url)
            .headers { it.setBearerAuth(authorizedClient.accessToken.tokenValue) } // TODO Waiting for proper solution by the Spring Security team
            .body(person)
            .retrieve()
            .body<Greeting>() ?: throw IllegalStateException("Empty response body")
    }
}