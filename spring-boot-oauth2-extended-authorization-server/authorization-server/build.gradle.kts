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
    implementation(libs.bundles.spring.web)
    implementation(libs.bundles.spring.thymeleaf)
    implementation(libs.bundles.spring.jdbc.h2)
    implementation(libs.spring.security.oauth2.authorization.server)
    implementation(libs.thymeleaf.extras.spring.security)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.test)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jvmVersion.toString()))
    }
}
