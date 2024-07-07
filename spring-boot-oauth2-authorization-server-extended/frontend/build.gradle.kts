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
    implementation(libs.bundles.spring.boot.web)
    implementation(libs.bundles.spring.boot.thymeleaf)
    implementation(libs.spring.boot.starter.oauth2.client)
    implementation(libs.thymeleaf.extras.spring.security)
    implementation(libs.bundles.webjars)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jvmVersion.toString()))
    }
}
