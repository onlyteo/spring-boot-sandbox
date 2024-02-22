package com.onlyteo.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record EventKey(@JsonProperty("id") @NotBlank String id) {
}
