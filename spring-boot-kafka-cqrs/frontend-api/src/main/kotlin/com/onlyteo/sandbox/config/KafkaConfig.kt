package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.ssl.SslBundles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConfig {

    @Bean
    fun buildPersonKafkaProducer(
        kafkaProperties: KafkaProperties,
        sslBundles: SslBundles
    ): KafkaProducer<String, Person> {
        return KafkaProducer(
            kafkaProperties.producer.buildProperties(sslBundles),
            StringSerializer(),
            buildJsonSerializer<Person>()
        )
    }

    @Bean
    fun buildGreetingKafkaConsumer(
        kafkaProperties: KafkaProperties,
        sslBundles: SslBundles
    ): KafkaConsumer<String, Greeting> {
        return KafkaConsumer(
            kafkaProperties.consumer.buildProperties(sslBundles),
            StringDeserializer(),
            buildJsonDeserializer<Greeting>()
        )
    }
}
