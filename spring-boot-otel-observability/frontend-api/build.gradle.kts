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
    implementation(libs.bundles.spring.micrometer.opentelemetry)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.test)
}
