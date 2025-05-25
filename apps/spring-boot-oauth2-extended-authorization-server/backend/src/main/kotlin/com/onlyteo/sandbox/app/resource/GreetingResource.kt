package com.onlyteo.sandbox.app.resource

import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.service.GreetingService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
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
    fun post(@RequestBody @Valid person: Person): ResponseEntity<Greeting> {
        val greeting = greetingService.getGreeting(person)
        return ResponseEntity.ok(greeting)
    }
}