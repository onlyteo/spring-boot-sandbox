val jvmMajorVersion: String by project
val jvmVersion = JavaVersion.toVersion(jvmMajorVersion)

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencies)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.logging)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.spring.kafka)
    implementation(libs.kafka.clients)
    implementation(libs.kafka.streams)
    testImplementation(libs.spring.boot.starter.test)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jvmVersion.toString()))
    }
}
