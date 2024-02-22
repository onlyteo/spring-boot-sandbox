package com.onlyteo.sandbox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyteo.sandbox.model.EventEnvelope;
import com.onlyteo.sandbox.model.EventKey;
import com.onlyteo.sandbox.model.EventValue;
import com.onlyteo.sandbox.model.StatusEvent;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration(proxyBeanMethods = false)
public class StatusKafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusKafkaConfig.class);

    @Bean
    public JsonSerde<EventEnvelope<EventValue<StatusEvent>>> statusEventValueSerde(final ObjectMapper objectMapper) {
        final var eventValueType = objectMapper.getTypeFactory().constructParametricType(EventValue.class, StatusEvent.class);
        final var eventEnvelopeType = objectMapper.getTypeFactory().constructParametricType(EventEnvelope.class, eventValueType);
        return new JsonSerde<>(eventEnvelopeType, objectMapper);
    }

    @Bean
    public ConsumerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<StatusEvent>>>
    statusKafkaConsumerFactory(final KafkaProperties kafkaProperties,
                               final ObjectProvider<SslBundles> sslBundles) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(sslBundles.getIfAvailable()));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<StatusEvent>>>
    statusKafkaListenerContainerFactory(
            @Qualifier("statusKafkaConsumerFactory") final ConsumerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<StatusEvent>>> consumerFactory) {
        final var factory = new ConcurrentKafkaListenerContainerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<StatusEvent>>>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public Topology statusKafkaStreamsTopology(StreamsBuilder streamsBuilder,
                                               AppKafkaProperties properties,
                                               @Qualifier("eventKeySerde") JsonSerde<EventEnvelope<EventKey>> keySerde,
                                               @Qualifier("statusEventValueSerde") JsonSerde<EventEnvelope<EventValue<StatusEvent>>> valueSerde) {
        final var statusKafkaStream = streamsBuilder.stream(properties.streams().accountSourceTopic(), Consumed.with(keySerde, valueSerde));

        statusKafkaStream
                .filter((key, value) -> key != null && value != null)
                .foreach((key, value) -> LOGGER.info("Received Account Change Event $key with value $value"));

        LOGGER.info("Created Status Kafka Streams Topology");

        return streamsBuilder.build();
    }
}
