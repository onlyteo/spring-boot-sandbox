package com.onlyteo.sandbox.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class CdcEnvelope<T>(@JsonProperty("payload") @field:NotNull val payload: T)