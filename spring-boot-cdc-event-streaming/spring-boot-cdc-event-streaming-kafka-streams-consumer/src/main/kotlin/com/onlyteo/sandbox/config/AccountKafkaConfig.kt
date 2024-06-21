package com.onlyteo.sandbox.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.onlyteo.sandbox.model.AccountEvent
import com.onlyteo.sandbox.model.EventEnvelope
import com.onlyteo.sandbox.model.EventKey
import com.onlyteo.sandbox.model.EventValue
import com.onlyteo.sandbox.properties.AppKafkaProperties
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.ssl.SslBundles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonSerde

@Configuration(proxyBeanMethods = false)
class AccountKafkaConfig {

    val logger: Logger = LoggerFactory.getLogger(AccountKafkaConfig::class.java)

    @Bean
    fun accountEventValueSerde(objectMapper: ObjectMapper): JsonSerde<EventEnvelope<EventValue<AccountEvent>>> {
        val eventValueType = objectMapper.typeFactory.constructParametricType(
            EventValue::class.java,
            AccountEvent::class.java
        )
        val eventEnvelopeType = objectMapper.typeFactory.constructParametricType(
            EventEnvelope::class.java, eventValueType
        )
        return JsonSerde(eventEnvelopeType, objectMapper)
    }

    @Bean
    fun accountKafkaConsumerFactory(
        kafkaProperties: KafkaProperties,
        sslBundles: ObjectProvider<SslBundles>
    ): ConsumerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>> {
        return DefaultKafkaConsumerFactory(kafkaProperties.buildConsumerProperties(sslBundles.getIfAvailable()))
    }

    @Bean
    fun accountKafkaListenerContainerFactory(
        @Qualifier("accountKafkaConsumerFactory") consumerFactory: ConsumerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>>
    ): ConcurrentKafkaListenerContainerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>> {
        val factory =
            ConcurrentKafkaListenerContainerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>>()
        factory.setConsumerFactory(consumerFactory)
        return factory
    }

    @Bean
    fun accountKafkaStreamsTopology(
        streamsBuilder: StreamsBuilder,
        properties: AppKafkaProperties,
        @Qualifier("eventKeySerde") keySerde: JsonSerde<EventEnvelope<EventKey>>,
        @Qualifier("accountEventValueSerde") valueSerde: JsonSerde<EventEnvelope<EventValue<AccountEvent>>>
    ): Topology {
        val statusKafkaStream =
            streamsBuilder.stream(properties.streams.accountSourceTopic, Consumed.with(keySerde, valueSerde))

        statusKafkaStream
            .filter { key, value -> key != null && value != null }
            .foreach { key, value -> logger.info("Received Account Change Event {} with value {}", key, value) }

        logger.info("Created Status Kafka Streams Topology")

        return streamsBuilder.build()
    }
}