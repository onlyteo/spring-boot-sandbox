plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    application
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.bundles.spring.web)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.webjars)
    implementation(libs.bundles.spring.kafka.streams)
    testImplementation(libs.bundles.spring.test)
}
