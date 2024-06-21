package com.onlyteo.sandbox.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.net.URL
import java.time.ZonedDateTime

data class StatusDto(
    @JsonProperty("id") @field:NotBlank val id: String,
    @JsonProperty("url") @field:NotNull val url: URL,
    @JsonProperty("language") @field:NotBlank val language: String,
    @JsonProperty("visibility") @field:NotBlank val visibility: String,
    @JsonProperty("content") @field:NotBlank val content: String,
    @JsonProperty("in_reply_to_id") val inReplyToStatusId: String,
    @JsonProperty("in_reply_to_account_id") val inReplyToAccountId: String,
    @JsonProperty("replies_count") @field:NotNull val repliesCount: Int,
    @JsonProperty("reblogs_count") @field:NotNull val reblogsCount: Int,
    @JsonProperty("favourites_count") @field:NotNull val favouritesCount: Int,
    @JsonProperty("created_at") @field:NotNull val createdAt: ZonedDateTime,
    @JsonProperty("account") @field:NotBlank val account: AccountDto
)
