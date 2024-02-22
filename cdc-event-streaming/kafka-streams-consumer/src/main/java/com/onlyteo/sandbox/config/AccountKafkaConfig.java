package com.onlyteo.sandbox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyteo.sandbox.model.AccountEvent;
import com.onlyteo.sandbox.model.EventEnvelope;
import com.onlyteo.sandbox.model.EventKey;
import com.onlyteo.sandbox.model.EventValue;
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
public class AccountKafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountKafkaConfig.class);

    @Bean
    public JsonSerde<EventEnvelope<EventValue<AccountEvent>>> accountEventValueSerde(final ObjectMapper objectMapper) {
        final var eventValueType = objectMapper.getTypeFactory().constructParametricType(EventValue.class, AccountEvent.class);
        final var eventEnvelopeType = objectMapper.getTypeFactory().constructParametricType(EventEnvelope.class, eventValueType);
        return new JsonSerde<>(eventEnvelopeType, objectMapper);
    }

    @Bean
    public ConsumerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>>
    accountKafkaConsumerFactory(final KafkaProperties kafkaProperties,
                                final ObjectProvider<SslBundles> sslBundles) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(sslBundles.getIfAvailable()));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>>
    accountKafkaListenerContainerFactory(
            @Qualifier("accountKafkaConsumerFactory") final ConsumerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>> consumerFactory) {
        final var factory = new ConcurrentKafkaListenerContainerFactory<EventEnvelope<EventKey>, EventEnvelope<EventValue<AccountEvent>>>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public Topology accountKafkaStreamsTopology(StreamsBuilder streamsBuilder,
                                                AppKafkaProperties properties,
                                                @Qualifier("eventKeySerde") JsonSerde<EventEnvelope<EventKey>> keySerde,
                                                @Qualifier("accountEventValueSerde") JsonSerde<EventEnvelope<EventValue<AccountEvent>>> valueSerde) {
        final var statusKafkaStream = streamsBuilder.stream(properties.streams().accountSourceTopic(), Consumed.with(keySerde, valueSerde));

        statusKafkaStream
                .filter((key, value) -> key != null && value != null)
                .foreach((key, value) -> LOGGER.info("Received Account Change Event $key with value $value"));

        LOGGER.info("Created Status Kafka Streams Topology");

        return streamsBuilder.build();
    }
}
