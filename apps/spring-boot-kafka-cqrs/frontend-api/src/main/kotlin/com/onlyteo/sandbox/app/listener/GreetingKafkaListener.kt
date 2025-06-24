package com.onlyteo.sandbox.app.listener

import com.onlyteo.sandbox.app.config.buildLogger
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.GreetingEvent
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class GreetingKafkaListener(
    private val applicationProperties: ApplicationProperties,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    private val logger = buildLogger

    @KafkaListener(topics = ["greetings"])
    fun listen(record: ConsumerRecord<String, Greeting>) {
        val consumerProperties = applicationProperties.kafka.consumer
        val greeting = record.value()

        logger.info(
            "Received greeting \"{}\" on topic \"{}\"",
            greeting.message,
            consumerProperties.sourceTopic
        )
        applicationEventPublisher.publishEvent(GreetingEvent(this, greeting))
    }
}