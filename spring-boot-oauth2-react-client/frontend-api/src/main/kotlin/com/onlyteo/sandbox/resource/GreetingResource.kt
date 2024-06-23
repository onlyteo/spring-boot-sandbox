package com.onlyteo.sandbox.resource

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.service.GreetingService
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/api/greetings"])
@RestController
class GreetingResource(
    val greetingService: GreetingService
) {

    @PostMapping
    fun post(
        @RequestBody person: Person,
        @RegisteredOAuth2AuthorizedClient("sandbox-oauth2-client") authorizedClient: OAuth2AuthorizedClient
    ): ResponseEntity<Greeting> {
        val greeting = greetingService.getGreeting(authorizedClient, person)
        return ResponseEntity.ok(greeting)
    }
}