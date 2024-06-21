package com.onlyteo.sandbox.model

import com.fasterxml.jackson.annotation.JsonProperty

data class EventValue<T>(
    @JsonProperty("before") val before: T,
    @JsonProperty("after") val after: T
)
