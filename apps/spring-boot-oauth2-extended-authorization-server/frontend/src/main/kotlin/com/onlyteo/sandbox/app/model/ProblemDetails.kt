package com.onlyteo.sandbox.app.model

import org.springframework.http.HttpStatus

/**
 * This is an error response class that implements the format of the Problem Details specification of RFC7807.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7807">IETF RFC7807</a>
 */
data class ProblemDetails(
    val type: String,
    val status: Int,
    val title: String,
    val detail: String,
    val instance: String
) {
    constructor(httpStatus: HttpStatus, detail: String, instance: String) : this(
        type = "about:blank",
        status = httpStatus.value(),
        title = httpStatus.reasonPhrase,
        detail = detail,
        instance = instance
    )
}
