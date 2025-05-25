package com.onlyteo.sandbox.app.config

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka


@EnableKafka
@Configuration(proxyBeanMethods = false)
class KafkaConfig
