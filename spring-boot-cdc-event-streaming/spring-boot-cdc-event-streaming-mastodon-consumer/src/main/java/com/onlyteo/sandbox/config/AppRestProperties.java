package com.onlyteo.sandbox.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties(prefix = "app.rest")
public record AppRestProperties(@NotNull AppConsumerProperties consumer) {

    public record AppConsumerProperties(@NotNull AppClientProperties mastodon) {

        public record AppClientProperties(@NotNull URL url,
                                          String bearerToken) {
        }
    }
}
