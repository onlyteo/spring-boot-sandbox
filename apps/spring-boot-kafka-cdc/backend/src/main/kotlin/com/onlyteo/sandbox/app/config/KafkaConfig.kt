package com.onlyteo.sandbox.app.config

import com.onlyteo.sandbox.app.model.CdcEnvelope
import com.onlyteo.sandbox.app.model.CdcKey
import com.onlyteo.sandbox.app.model.CdcValue
import com.onlyteo.sandbox.app.model.Greeting
import com.onlyteo.sandbox.app.model.PersonEntity
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.service.GreetingService
import com.onlyteo.sandbox.app.topology.buildKafkaStreamsTopology
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.boot.kafka.autoconfigure.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.support.serializer.JacksonJsonSerde

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
        kafkaProperties: KafkaProperties
    ): Serde<CdcEnvelope<CdcKey>> {
        val jsonSerde = JacksonJsonSerde<CdcEnvelope<CdcKey>>()
        jsonSerde.configure(kafkaProperties.streams.properties, true)
        return jsonSerde
    }

    @Bean
    fun sourceValueSerde(
        kafkaProperties: KafkaProperties
    ): Serde<CdcEnvelope<CdcValue<PersonEntity>>> {
        val jsonSerde = JacksonJsonSerde<CdcEnvelope<CdcValue<PersonEntity>>>()
        jsonSerde.configure(kafkaProperties.streams.properties, false)
        return jsonSerde
    }

    @Bean
    fun sinkKeySerde(): Serde<String> {
        return Serdes.String()
    }

    @Bean
    fun sinkValueSerde(
        kafkaProperties: KafkaProperties
    ): Serde<Greeting> {
        val jsonSerde = JacksonJsonSerde<Greeting>()
        jsonSerde.configure(kafkaProperties.streams.properties, false)
        return jsonSerde
    }
}