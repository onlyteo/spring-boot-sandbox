package com.onlyteo.sandbox.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka")
public record AppKafkaProperties(@NotNull AppKafkaStreamsProperties streams) {

    public record AppKafkaStreamsProperties(@NotBlank String accountSourceTopic,
                                            @NotBlank String statusSourceTopic) {
    }
}
