package com.onlyteo.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.net.URL;
import java.time.ZonedDateTime;

public record AccountEvent(@JsonProperty("id") @NotBlank String id,
                           @JsonProperty("url") @NotNull URL url,
                           @JsonProperty("avatar") @NotNull URL avatar,
                           @JsonProperty("username") @NotBlank String username,
                           @JsonProperty("account") @NotBlank String account,
                           @JsonProperty("display_name") @NotBlank String displayName,
                           @JsonProperty("followers_count") @NotNull Integer followersCount,
                           @JsonProperty("following_count") @NotNull Integer followingCount,
                           @JsonProperty("statuses_count") @NotNull Integer statusesCount,
                           @JsonProperty("created_at") @NotNull ZonedDateTime createdAt) {
}
