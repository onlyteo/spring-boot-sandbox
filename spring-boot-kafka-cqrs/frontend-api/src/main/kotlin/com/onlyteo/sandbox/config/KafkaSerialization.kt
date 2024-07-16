package com.onlyteo.sandbox.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer

inline fun <reified T> buildJsonSerializer(objectMapper: ObjectMapper) = object : Serializer<T> {
    override fun serialize(topic: String?, data: T): ByteArray {
        if (data == null) return byteArrayOf()
        return objectMapper.writeValueAsBytes(data)
    }
}

inline fun <reified T> buildJsonSerializer(): Serializer<T> {
    return buildJsonSerializer<T>(buildObjectMapper)
}

inline fun <reified T> buildJsonDeserializer(objectMapper: ObjectMapper) = object : Deserializer<T> {
    override fun deserialize(topic: String?, data: ByteArray?): T? {
        if (data == null) return null
        return objectMapper.readValue<T>(data)
    }
}

inline fun <reified T> buildJsonDeserializer(): Deserializer<T> {
    return buildJsonDeserializer<T>(buildObjectMapper)
}
