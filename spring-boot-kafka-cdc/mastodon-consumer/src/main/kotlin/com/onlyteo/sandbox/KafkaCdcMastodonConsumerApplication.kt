package com.onlyteo.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaCdcMastodonConsumerApplication

fun main(args: Array<String>) {
    runApplication<KafkaCdcMastodonConsumerApplication>(*args)
}