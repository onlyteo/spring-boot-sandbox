package com.onlyteo.sandbox.app.reader

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.FileReader
import kotlin.io.path.toPath

val csvMapper = CsvMapper().apply {
    registerKotlinModule {}
}

inline fun <reified T : Any> readCsvFile(path: String): List<T> {
    val resource = requireNotNull(object {}::class.java.getResource(path)) { "Resource not found: $path" }
    return readCsvFile<T>(resource.toURI().toPath().toFile())
}

inline fun <reified T : Any> readCsvFile(file: File): List<T> {
    FileReader(file).use { reader ->
        return csvMapper
            .readerFor(T::class.java)
            .with(CsvSchema.emptySchema().withHeader())
            .readValues<T>(reader)
            .readAll()
            .toList()
    }
}