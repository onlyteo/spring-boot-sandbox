package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.service.GreetingService
import com.onlyteo.sandbox.topology.buildKafkaStreamsTopology
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams

@EnableKafka
@EnableKafkaStreams
@Configuration(proxyBeanMethods = false)
class KafkaConfig {

    @Bean
    fun kafkaTopology(
        streamsBuilder: StreamsBuilder,
        applicationProperties: ApplicationProperties,
        greetingService: GreetingService
    ): Topology {
        with(streamsBuilder) {
            return buildKafkaStreamsTopology(applicationProperties.kafka.streams, greetingService)
        }
    }
}