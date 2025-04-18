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
    implementation(libs.spring.boot.starter.oauth2.resource.server)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.test)
}
