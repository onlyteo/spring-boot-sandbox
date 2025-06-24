package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.app.config.buildLogger
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.model.PersonEntity
import com.onlyteo.sandbox.app.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class GreetingService(
    private val personRepository: PersonRepository
) {
    private val logger = buildLogger

    fun sendGreeting(person: Person) {
        logger.info("Saving person \"{}\" on table \"PERSON\"", person.name)
        val personEntity = personRepository.findByName(person.name)
        if (personEntity == null) {
            personRepository.save(PersonEntity(name = person.name, count = 1))
        } else {
            personEntity.increaseCount()
            personRepository.save(personEntity)
        }
    }
}