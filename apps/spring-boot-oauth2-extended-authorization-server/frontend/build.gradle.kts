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
    implementation(libs.spring.boot.starter.oauth2.client)
    implementation(libs.thymeleaf.extras.spring.security)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.boot.test)
}
