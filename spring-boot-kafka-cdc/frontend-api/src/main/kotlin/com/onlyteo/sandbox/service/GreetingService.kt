package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.PersonEntity
import com.onlyteo.sandbox.repository.PersonRepository
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