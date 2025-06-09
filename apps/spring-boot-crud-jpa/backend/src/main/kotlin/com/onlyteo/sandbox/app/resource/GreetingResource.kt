package com.onlyteo.sandbox.app.resource

import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.service.GreetingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/api/greetings"])
@RestController
class GreetingResource(
    val greetingService: GreetingService
) {
    @GetMapping
    fun get(@RequestParam("name") name: String): ResponseEntity<List<Greeting>> {
        val greetings = greetingService.findGreetings(name)
        return ResponseEntity.ok(greetings)
    }

    @PostMapping
    fun post(@RequestBody person: Person): ResponseEntity<Greeting> {
        val greeting = greetingService.getGreeting(person.name)
        return ResponseEntity.ok(greeting)
    }
}