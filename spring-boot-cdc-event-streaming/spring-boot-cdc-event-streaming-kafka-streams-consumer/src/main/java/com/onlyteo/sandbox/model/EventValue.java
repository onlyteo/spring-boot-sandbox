package com.onlyteo.sandbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventValue<T>(@JsonProperty("before") T before,
                            @JsonProperty("after") T after) {
}
