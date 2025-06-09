plugins {
    kotlin("jvm")
    kotlin("plugin.noarg")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    application
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.bundles.spring.web)
    implementation(libs.bundles.spring.jpa.mysql)
    implementation(libs.bundles.spring.jpa.postgresql)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.test)
}

noArg {
    annotation("jakarta.persistence.Entity")
}
