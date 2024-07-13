package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CdcEventStreamingMastodonConsumerApplication

fun main(args: Array<String>) {
    runApplication<CdcEventStreamingMastodonConsumerApplication>(*args)
}