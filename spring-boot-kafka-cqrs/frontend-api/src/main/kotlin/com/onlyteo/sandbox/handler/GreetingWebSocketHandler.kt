package com.onlyteo.sandbox.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.properties.ApplicationProperties
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

@Component
class GreetingWebSocketHandler(
    private val applicationProperties: ApplicationProperties,
    private val objectMapper: ObjectMapper,
    private val greetingKafkaConsumer: KafkaConsumer<String, Greeting>
) : TextWebSocketHandler() {

    private val logger = buildLogger
    private val semaphore = AtomicBoolean(true)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val consumerProperties = applicationProperties.kafka.consumer

        try {
            logger.info("Kafka consumer starting subscription on topic {}", consumerProperties.sourceTopic)
            greetingKafkaConsumer.subscribe(listOf(consumerProperties.sourceTopic))
            while (true) {
                greetingKafkaConsumer.poll(Duration.ofMillis(500))
                    .forEach { record ->
                        val greeting = record.value()
                        logger.info(
                            "Received greeting \"{}\" on topic \"{}\"",
                            greeting.message,
                            consumerProperties.sourceTopic
                        )
                        session.sendMessage(TextMessage(objectMapper.writeValueAsString(greeting)))
                    }
            }
        } finally {
            logger.info("Kafka consumer finished subscription on topic {}", consumerProperties.sourceTopic)
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        semaphore.set(false)
    }
}