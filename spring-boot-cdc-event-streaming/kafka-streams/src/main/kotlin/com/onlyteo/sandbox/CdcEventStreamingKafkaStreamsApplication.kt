package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CdcEventStreamingKafkaStreamsApplication

fun main(args: Array<String>) {
    runApplication<CdcEventStreamingKafkaStreamsApplication>(*args)
}