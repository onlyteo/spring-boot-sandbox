package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootCdcEventStreamingKafkaStreamsApplication

fun main(args: Array<String>) {
    runApplication<SpringBootCdcEventStreamingKafkaStreamsApplication>(*args)
}