package com.onlyteo.sandbox.model

import jakarta.validation.constraints.NotBlank

data class Greeting(@field:NotBlank val message: String)
