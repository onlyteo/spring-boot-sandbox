package com.onlyteo.sandbox.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.onlyteo.sandbox.model.EventEnvelope
import com.onlyteo.sandbox.model.EventKey
import com.onlyteo.sandbox.properties.AppKafkaProperties
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.support.serializer.JsonSerde

@EnableConfigurationProperties(AppKafkaProperties::class)
@EnableKafkaStreams
@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConfig {

    @Bean
    fun stringSerde(): Serde<String> {
        return Serdes.String()
    }

    @Bean
    fun eventKeySerde(objectMapper: ObjectMapper): JsonSerde<EventEnvelope<EventKey>> {
        val type = objectMapper.typeFactory.constructParametricType(
            EventEnvelope::class.java,
            EventKey::class.java
        )
        return JsonSerde(type, objectMapper)
    }
}