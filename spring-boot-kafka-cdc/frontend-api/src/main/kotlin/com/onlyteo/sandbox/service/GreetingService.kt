package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.ApplicationProperties
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