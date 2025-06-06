package com.onlyteo.sandbox.app.model

import org.springframework.context.ApplicationEvent

data class GreetingEvent(
    private val source: Any,
    val greeting: Greeting
) : ApplicationEvent(source)