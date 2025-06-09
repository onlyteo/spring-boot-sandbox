package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.GreetingEntity
import com.onlyteo.sandbox.app.model.PersonEntity
import com.onlyteo.sandbox.app.model.toGreeting
import com.onlyteo.sandbox.app.repository.GreetingRepository
import com.onlyteo.sandbox.app.repository.PersonRepository
import com.onlyteo.sandbox.app.repository.PrefixRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GreetingService(
    private val prefixRepository: PrefixRepository,
    private val personRepository: PersonRepository,
    private val greetingRepository: GreetingRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(GreetingService::class.java)

    @Transactional
    fun getGreeting(name: String): Greeting {
        val personEntity = personRepository.findByName(name) ?: personRepository.save(PersonEntity(name = name))
        val prefix = prefixRepository.getPrefix()
        val greetingEntity = GreetingEntity(
            message = "${prefix.greeting} ${name}!",
            person = personEntity,
        )
        val savedGreetingEntity = greetingRepository.save(greetingEntity)
        logger.info("Returning greeting to \"{}\"", name)
        return savedGreetingEntity.toGreeting()
    }

    fun findGreetings(name: String): List<Greeting> {
        val greetingEntities = greetingRepository.findByPerson_Name(name)
        logger.info("Returning all greetings for \"{}\"", name)
        return greetingEntities.map { it.toGreeting() }
    }
}