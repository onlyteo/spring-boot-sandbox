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
    implementation(libs.bundles.spring.boot.web)
    implementation(libs.bundles.spring.boot.jpa.mysql)
    implementation(libs.bundles.spring.boot.jpa.postgresql)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.boot.test)
}

noArg {
    annotation("jakarta.persistence.Entity")
}
