package com.onlyteo.sandbox.model

import jakarta.validation.constraints.NotBlank

data class FormData(@field:NotBlank val name: String?) {
    fun toPerson(): Person {
        return if (name != null) Person(name) else throw IllegalArgumentException("Name is null")
    }
}
