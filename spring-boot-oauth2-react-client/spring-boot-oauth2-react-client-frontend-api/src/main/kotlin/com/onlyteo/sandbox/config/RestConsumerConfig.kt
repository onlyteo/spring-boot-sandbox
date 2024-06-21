package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.AppRestProperties
import jakarta.validation.Valid
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@EnableConfigurationProperties(AppRestProperties::class)
@Configuration(proxyBeanMethods = false)
class RestConsumerConfig {

    @Bean
    fun backendRestClient(@Valid properties: AppRestProperties): RestClient {
        return RestClient.builder()
            .baseUrl(properties.consumer.backend.url.toString())
            .build()
    }
}