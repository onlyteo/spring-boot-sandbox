package com.onlyteo.sandbox.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.net.URL
import java.time.ZonedDateTime

data class AccountEvent(
    @JsonProperty("id") @field:NotBlank val id: String,
    @JsonProperty("url") @field:NotNull val url: URL,
    @JsonProperty("avatar") @field:NotNull val avatar: URL,
    @JsonProperty("username") @field:NotBlank val username: String,
    @JsonProperty("account") @field:NotBlank val account: String,
    @JsonProperty("display_name") @field:NotBlank val displayName: String,
    @JsonProperty("followers_count") @field:NotNull val followersCount: Int,
    @JsonProperty("following_count") @field:NotNull val followingCount: Int,
    @JsonProperty("statuses_count") @field:NotNull val statusesCount: Int,
    @JsonProperty("created_at") @field:NotNull val createdAt: ZonedDateTime
)
