package com.onlyteo.sandbox.app.config

import com.onlyteo.sandbox.app.model.Greeting
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.ssl.SslBundles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.CommonLoggingErrorHandler


//@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConfig {

    @Bean("greetingKafkaListenerContainerFactory")
    fun greetingKafkaListenerContainerFactory(
        @Qualifier("greetingKafkaConsumerFactory") consumerFactory: ConsumerFactory<String, Greeting>
    ): ConcurrentKafkaListenerContainerFactory<String, Greeting> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Greeting>()
        factory.consumerFactory = consumerFactory
        factory.setCommonErrorHandler(CommonLoggingErrorHandler())
        return factory
    }

    @Bean("greetingKafkaConsumerFactory")
    fun greetingKafkaConsumerFactory(
        kafkaProperties: KafkaProperties,
        sslBundlesBean: ObjectProvider<SslBundles>
    ): ConsumerFactory<String, Greeting> {
        return DefaultKafkaConsumerFactory(kafkaProperties.buildConsumerProperties(sslBundlesBean.getIfAvailable()))
    }
}
