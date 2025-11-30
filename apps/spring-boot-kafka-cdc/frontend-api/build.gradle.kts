plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    application
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.bundles.spring.boot.web)
    implementation(libs.bundles.spring.boot.kafka)
    implementation(libs.bundles.spring.boot.websocket)
    implementation(libs.bundles.spring.boot.jpa.mysql)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.boot.test)
}
