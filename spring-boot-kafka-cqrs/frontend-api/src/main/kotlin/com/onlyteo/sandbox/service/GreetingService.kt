package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.properties.ApplicationProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.stereotype.Service

@Service
class GreetingService(
    private val applicationProperties: ApplicationProperties,
    private val personKafkaProducer: KafkaProducer<String, Person>
) {
    private val logger = buildLogger

    fun sendGreeting(person: Person) {
        val producerProperties = applicationProperties.kafka.producer
        logger.info("Sending person \"{}\" on topic \"{}\"", person.name, producerProperties.targetTopic)
        personKafkaProducer.send(ProducerRecord(producerProperties.targetTopic, person.name, person))
    }
}