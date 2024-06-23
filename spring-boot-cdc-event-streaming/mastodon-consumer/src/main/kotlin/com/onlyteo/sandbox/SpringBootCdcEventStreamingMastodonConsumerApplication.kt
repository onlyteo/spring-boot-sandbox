package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootCdcEventStreamingMastodonConsumerApplication

fun main(args: Array<String>) {
    runApplication<SpringBootCdcEventStreamingMastodonConsumerApplication>(*args)
}