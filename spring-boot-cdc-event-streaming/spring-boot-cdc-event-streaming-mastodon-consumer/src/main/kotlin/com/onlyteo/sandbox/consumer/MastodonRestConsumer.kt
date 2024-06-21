package com.onlyteo.sandbox.consumer

import com.onlyteo.sandbox.model.SearchResultDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriBuilder

@Component
class MastodonRestConsumer(
    @Qualifier("mastodonRestClient") private val restClient: RestClient
) {
    fun search(
        @NotBlank query: String?,
        @NotBlank type: String?,
        @NotNull offset: Int?,
        @NotNull limit: Int?
    ): ResponseEntity<SearchResultDto> {
        return restClient.get()
            .uri { url: UriBuilder ->
                url.path("/api/v2/search")
                    .queryParam("q", query)
                    .queryParam("type", type)
                    .queryParam("offset", offset)
                    .queryParam("limit", limit)
                    .build()
            }
            .retrieve()
            .toEntity(SearchResultDto::class.java)
    }
}