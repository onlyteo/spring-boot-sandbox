package com.onlyteo.sandbox.app.config

import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration(proxyBeanMethods = false)
class RestConsumerConfig {

    @Bean
    fun backendRestClient(
        builder: RestClient.Builder,
        properties: ApplicationProperties
    ): RestClient {
        return builder
            .baseUrl(properties.integrations.backend.url.toString())
            .build()
    }
}