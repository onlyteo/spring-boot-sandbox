package com.onlyteo.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.net.URL;
import java.time.ZonedDateTime;

public record StatusEvent(@JsonProperty("id") @NotBlank String id,
                          @JsonProperty("url") @NotNull URL url,
                          @JsonProperty("language") @NotBlank String language,
                          @JsonProperty("visibility") @NotBlank String visibility,
                          @JsonProperty("content") @NotBlank String content,
                          @JsonProperty("in_reply_to_id") String inReplyToStatusId,
                          @JsonProperty("in_reply_to_account_id") String inReplyToAccountId,
                          @JsonProperty("replies_count") @NotNull Integer repliesCount,
                          @JsonProperty("reblogs_count") @NotNull Integer reblogsCount,
                          @JsonProperty("favourites_count") @NotNull Integer favouritesCount,
                          @JsonProperty("created_at") @NotNull ZonedDateTime createdAt,
                          @JsonProperty("account_id") @NotBlank String accountId) {
}
