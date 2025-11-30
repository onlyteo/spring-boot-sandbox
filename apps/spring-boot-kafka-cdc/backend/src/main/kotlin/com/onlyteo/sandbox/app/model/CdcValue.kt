package com.onlyteo.sandbox.app.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CdcValue<T>(
    @field:JsonProperty("before") val before: T?,
    @field:JsonProperty("after") val after: T?
)
