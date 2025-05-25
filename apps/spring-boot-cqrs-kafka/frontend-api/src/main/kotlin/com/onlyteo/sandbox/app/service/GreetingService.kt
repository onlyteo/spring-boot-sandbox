package com.onlyteo.sandbox.app.service

import com.onlyteo.sandbox.app.config.buildLogger
import com.onlyteo.sandbox.app.model.Person
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class GreetingService(
    private val applicationProperties: ApplicationProperties,
    private val kafkaTemplate: KafkaTemplate<Any, Any>
) {
    private val logger = buildLogger

    fun sendGreeting(person: Person) {
        val producerProperties = applicationProperties.kafka.producer
        logger.info("Sending person \"{}\" on topic \"{}\"", person.name, producerProperties.targetTopic)
        kafkaTemplate.send(producerProperties.targetTopic, person.name, person)
    }
}