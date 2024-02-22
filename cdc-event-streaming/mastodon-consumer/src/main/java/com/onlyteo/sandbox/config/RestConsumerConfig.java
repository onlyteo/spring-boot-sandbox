package com.onlyteo.sandbox.config;

import jakarta.validation.Valid;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@EnableConfigurationProperties(AppRestProperties.class)
@Configuration(proxyBeanMethods = false)
public class RestConsumerConfig {

    @Bean
    public RestClient mastodonRestClient(@Valid final AppRestProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.consumer().mastodon().url().toString())
                .defaultHeaders(headers -> headers.setBearerAuth(properties.consumer().mastodon().bearerToken()))
                .build();
    }
}
