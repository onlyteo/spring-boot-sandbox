package com.onlyteo.sandbox.consumer;

import com.onlyteo.sandbox.model.SearchResultDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MastodonRestConsumer {

    private final RestClient client;

    public MastodonRestConsumer(@Qualifier("mastodonRestClient") final RestClient client) {
        this.client = client;
    }

    public ResponseEntity<SearchResultDto> search(@NotBlank final String query,
                                                  @NotBlank final String type,
                                                  @NotNull final Integer offset,
                                                  @NotNull final Integer limit) {
        return client.get()
                .uri(url -> url.path("/api/v2/search")
                        .queryParam("q", query)
                        .queryParam("type", type)
                        .queryParam("offset", offset)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .toEntity(SearchResultDto.class);
    }
}
