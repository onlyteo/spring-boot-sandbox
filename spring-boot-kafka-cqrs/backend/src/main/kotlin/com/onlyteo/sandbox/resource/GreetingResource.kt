package com.onlyteo.sandbox.resource

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.service.GreetingService
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
    fun post(@RequestBody person: Person): ResponseEntity<Greeting> {
        val greeting = greetingService.getGreeting(person)
        return ResponseEntity.ok(greeting)
    }
}