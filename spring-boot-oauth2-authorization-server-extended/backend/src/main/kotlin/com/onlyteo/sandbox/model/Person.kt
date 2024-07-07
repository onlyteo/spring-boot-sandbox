package com.onlyteo.sandbox.model

import jakarta.validation.constraints.NotBlank

data class Person(@field:NotBlank val name: String)

fun Person.toGreeting() = Greeting("Hello $name!")
