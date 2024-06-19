package com.onlyteo.sandbox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyteo.sandbox.model.EventEnvelope;
import com.onlyteo.sandbox.model.EventKey;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@EnableConfigurationProperties(AppKafkaProperties.class)
@EnableKafkaStreams
@EnableKafka
@Configuration(proxyBeanMethods = false)
public class KafkaConfig {

    @Bean
    public Serde<String> stringSerde() {
        return Serdes.String();
    }

    @Bean
    public JsonSerde<EventEnvelope<EventKey>> eventKeySerde(final ObjectMapper objectMapper) {
        final var type = objectMapper.getTypeFactory().constructParametricType(EventEnvelope.class, EventKey.class);
        return new JsonSerde<>(type, objectMapper);
    }
}
