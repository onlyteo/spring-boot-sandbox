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
    implementation(libs.bundles.spring.boot.thymeleaf)
    implementation(libs.thymeleaf.extras.spring.security)
    implementation(libs.bundles.spring.boot.jdbc.h2)
    implementation(libs.spring.boot.starter.oauth2.authorization.server)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.boot.test)
}
