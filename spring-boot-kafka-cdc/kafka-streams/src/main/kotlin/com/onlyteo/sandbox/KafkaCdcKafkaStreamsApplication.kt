package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaCdcKafkaStreamsApplication

fun main(args: Array<String>) {
    runApplication<KafkaCdcKafkaStreamsApplication>(*args)
}