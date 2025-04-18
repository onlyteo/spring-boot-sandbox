package com.onlyteo.sandbox.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.onlyteo.sandbox.model.*
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.service.GreetingService
import com.onlyteo.sandbox.topology.buildKafkaStreamsTopology
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.support.serializer.JsonSerde

@EnableKafkaStreams
@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConfig {

    @Bean
    fun kafkaTopology(
        streamsBuilder: StreamsBuilder,
        applicationProperties: ApplicationProperties,
        sourceKeySerde: Serde<CdcEnvelope<CdcKey>>,
        sourceValueSerde: Serde<CdcEnvelope<CdcValue<PersonEntity>>>,
        sinkKeySerde: Serde<String>,
        sinkValueSerde: Serde<Greeting>,
        greetingService: GreetingService
    ): Topology {
        with(streamsBuilder) {
            return buildKafkaStreamsTopology(
                applicationProperties.kafka.streams,
                Consumed.with(sourceKeySerde, sourceValueSerde),
                Produced.with(sinkKeySerde, sinkValueSerde),
                greetingService
            )
        }
    }

    @Bean
    fun sourceKeySerde(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper
    ): Serde<CdcEnvelope<CdcKey>> {
        val cdcEnvelopeType = objectMapper.typeFactory.constructParametricType(
            CdcEnvelope::class.java,
            CdcKey::class.java
        )
        val jsonSerde = JsonSerde<CdcEnvelope<CdcKey>>(cdcEnvelopeType, objectMapper)
        jsonSerde.configure(kafkaProperties.streams.properties, true)
        return jsonSerde
    }

    @Bean
    fun sourceValueSerde(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper
    ): Serde<CdcEnvelope<CdcValue<PersonEntity>>> {
        val cdcValueType = objectMapper.typeFactory.constructParametricType(
            CdcValue::class.java,
            PersonEntity::class.java
        )
        val cdcEnvelopeType = objectMapper.typeFactory.constructParametricType(
            CdcEnvelope::class.java,
            cdcValueType
        )
        val jsonSerde = JsonSerde<CdcEnvelope<CdcValue<PersonEntity>>>(cdcEnvelopeType, objectMapper)
        jsonSerde.configure(kafkaProperties.streams.properties, false)
        return jsonSerde
    }

    @Bean
    fun sinkKeySerde(): Serde<String> {
        return Serdes.String()
    }

    @Bean
    fun sinkValueSerde(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper
    ): Serde<Greeting> {
        val jsonSerde = JsonSerde<Greeting>(objectMapper)
        jsonSerde.configure(kafkaProperties.streams.properties, false)
        return jsonSerde
    }
}