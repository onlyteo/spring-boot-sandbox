package com.onlyteo.sandbox.resource;

import com.onlyteo.sandbox.consumer.MastodonRestConsumer;
import com.onlyteo.sandbox.service.AccountService;
import com.onlyteo.sandbox.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/statuses")
@RestController
public class StatusResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusResource.class);
    private final AccountService accountService;
    private final StatusService statusService;
    private final MastodonRestConsumer mastodonRestConsumer;

    public StatusResource(final AccountService accountService,
                          final StatusService statusService,
                          final MastodonRestConsumer mastodonRestConsumer) {
        this.accountService = accountService;
        this.statusService = statusService;
        this.mastodonRestConsumer = mastodonRestConsumer;
    }

    @PostMapping
    public void post(@RequestParam(name = "query", defaultValue = "tech") final String query,
                     @RequestParam(name = "type", defaultValue = "statuses") final String type,
                     @RequestParam(name = "offset", defaultValue = "0") final Integer offset,
                     @RequestParam(name = "limit", defaultValue = "10") final Integer limit) {
        final var responseEntity = mastodonRestConsumer.search(query, type, offset, limit);
        if (responseEntity.getBody() == null) {
            LOGGER.info("No response body returned");
        } else if (responseEntity.getBody().statuses().isEmpty()) {
            LOGGER.warn("Response body contained no statuses");
        } else {
            responseEntity.getBody().statuses().forEach(status -> {
                accountService.upsert(status.account());
                statusService.upsert(status);
            });
        }
    }
}
