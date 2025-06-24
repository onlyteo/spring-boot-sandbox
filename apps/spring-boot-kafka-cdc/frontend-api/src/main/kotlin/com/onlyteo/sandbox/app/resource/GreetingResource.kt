package com.onlyteo.sandbox.app.resource

import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.service.GreetingService
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
    fun post(@RequestBody person: Person): ResponseEntity<Void> {
        greetingService.sendGreeting(person)
        return ResponseEntity.accepted().build()
    }
}