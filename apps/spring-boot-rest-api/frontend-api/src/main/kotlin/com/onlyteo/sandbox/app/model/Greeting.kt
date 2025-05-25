package com.onlyteo.sandbox.app.model

import jakarta.validation.constraints.NotBlank

data class Greeting(@field:NotBlank val message: String)
