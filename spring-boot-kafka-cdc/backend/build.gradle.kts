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
    implementation(libs.bundles.spring.webflux)
    implementation(libs.bundles.spring.kafka.streams)
    testImplementation(libs.bundles.spring.test)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jvmVersion.toString()))
    }
}