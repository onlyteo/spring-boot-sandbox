package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.model.Prefix
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.reader.readCsvFile
import org.springframework.stereotype.Repository
import kotlin.random.Random

@Repository
class PrefixRepository(properties: ApplicationProperties) {

    private val prefixes: List<Prefix> = readCsvFile(properties.resources.prefixesFile)

    fun getPrefix(): Prefix {
        val index = Random.nextInt(prefixes.size)
        return prefixes[index]
    }
}