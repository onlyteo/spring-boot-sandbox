package com.onlyteo.sandbox.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CdcValue<T>(
    @JsonProperty("before") val before: T?,
    @JsonProperty("after") val after: T?
)
