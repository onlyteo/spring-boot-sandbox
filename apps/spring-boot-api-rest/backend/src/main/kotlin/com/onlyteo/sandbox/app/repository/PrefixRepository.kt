package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.app.model.Prefix
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.reader.readCsvFile
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