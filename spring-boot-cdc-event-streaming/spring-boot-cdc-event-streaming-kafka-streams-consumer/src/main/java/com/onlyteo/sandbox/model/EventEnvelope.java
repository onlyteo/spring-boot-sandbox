package com.onlyteo.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record EventEnvelope<T>(@JsonProperty("payload") @NotNull T payload) {
}
