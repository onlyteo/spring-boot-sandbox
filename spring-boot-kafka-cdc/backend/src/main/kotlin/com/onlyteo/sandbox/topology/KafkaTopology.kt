package com.onlyteo.sandbox.topology

import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.model.*
import com.onlyteo.sandbox.properties.KafkaStreamsProperties
import com.onlyteo.sandbox.service.GreetingService
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Named
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.processor.PunctuationType
import org.apache.kafka.streams.processor.Punctuator
import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.ProcessorContext
import org.apache.kafka.streams.processor.api.ProcessorSupplier
import org.apache.kafka.streams.processor.api.Record
import org.apache.kafka.streams.state.Stores
import org.apache.kafka.streams.state.TimestampedKeyValueStore
import org.apache.kafka.streams.state.ValueAndTimestamp
import org.springframework.kafka.support.serializer.JsonSerde
import java.time.Duration
import java.time.Instant

fun StreamsBuilder.buildKafkaStreamsTopology(
    properties: KafkaStreamsProperties,
    consumed: Consumed<CdcEnvelope<CdcKey>, CdcEnvelope<CdcValue<PersonEntity>>>,
    produced: Produced<String, Greeting>,
    greetingService: GreetingService
): Topology {
    val logger = buildLogger

    this.addStateStore(
        Stores.timestampedKeyValueStoreBuilder(
            Stores.inMemoryKeyValueStore(properties.stateStore),
            Serdes.StringSerde(),
            JsonSerde(PersonEntity::class.java)
        )
    )

    this.stream(properties.sourceTopic, consumed)
        .peek { key, _ -> logger.info("Received CDC event \"${key.payload.id}\" on Kafka topic \"${properties.sourceTopic}\"") }
        .filter { _, value -> value.payload.after != null } // Remove "delete" events
        .map { key, value -> KeyValue(key.payload.id, value.payload.after) }
        .process(
            buildPersonProcessorSupplier(properties, greetingService),
            Named.`as`(properties.processor),
            properties.stateStore
        )
        .peek { _, greeting -> logger.info("Sending greeting \"${greeting.message}\" to Kafka topic \"${properties.sinkTopic}\"") }
        .to(properties.sinkTopic, produced)

    return build()
}

private fun buildPersonProcessorSupplier(
    properties: KafkaStreamsProperties,
    greetingService: GreetingService
) =
    ProcessorSupplier { buildPersonProcessor(properties, greetingService) }

private fun buildPersonProcessor(
    properties: KafkaStreamsProperties,
    greetingService: GreetingService
) =
    object : Processor<String, PersonEntity, String, Greeting> {

        private val logger = buildLogger
        private lateinit var processorContext: ProcessorContext<String, Greeting>
        private lateinit var keyValueStore: TimestampedKeyValueStore<String, PersonEntity>

        override fun init(processorContext: ProcessorContext<String, Greeting>?) {
            this.processorContext = checkNotNull(processorContext) { "ProcessorContext cannot be null" }
            this.keyValueStore = checkNotNull(processorContext.getStateStore(properties.stateStore)) {
                "No StateStore named ${properties.stateStore} found"
            }
            processorContext.schedule(
                Duration.ofSeconds(10),
                PunctuationType.WALL_CLOCK_TIME,
                buildPersonPunctuator(processorContext, keyValueStore, greetingService)
            )
        }

        override fun process(record: Record<String, PersonEntity>?) {
            val person = record?.value() ?: return
            if (setOf("John", "Julie", "James", "Jenny").contains(person.name)) {
                // Delay greeting for these names by 1 minute
                logger.info("Delaying processing for \"{}\"", person.name)
                this.keyValueStore.put(person.name, ValueAndTimestamp.make(person, Instant.now().toEpochMilli()))
            } else {
                // Otherwise forward greeting
                logger.info("Completed processing for \"{}\"", person.name)
                val greeting = greetingService.getGreeting(person.name)
                this.processorContext.forward(Record(person.name, greeting, Instant.now().toEpochMilli()))
            }
        }
    }

private fun buildPersonPunctuator(
    processorContext: ProcessorContext<String, Greeting>,
    keyValueStore: TimestampedKeyValueStore<String, PersonEntity>,
    greetingService: GreetingService
) = object : Punctuator {

    private val logger = buildLogger

    override fun punctuate(timestamp: Long) {
        val keyValueIterator = keyValueStore.all()
        while (keyValueIterator.hasNext()) {
            keyValueIterator.next().apply {
                val greetingTime = Instant.ofEpochMilli(value.timestamp())
                if (Instant.now().minusSeconds(60).isAfter(greetingTime)) {
                    val person = value.value()
                    logger.info("Completed delayed processing for \"{}\"", person.name)
                    keyValueStore.delete(person.name)
                    val greeting = greetingService.getGreeting(person.name)
                    processorContext.forward(Record(person.name, greeting, Instant.now().toEpochMilli()))
                }
            }
        }
    }
}

class KafkaStreamsExceptionHandler : StreamsUncaughtExceptionHandler {

    private val logger = buildLogger

    override fun handle(throwable: Throwable): StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse {
        logger.error("Uncaught Kafka Streams exception", throwable)
        return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION
    }
}
