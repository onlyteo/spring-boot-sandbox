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
    implementation(libs.bundles.spring.thymeleaf)
    implementation(libs.thymeleaf.extras.spring.security)
    implementation(libs.bundles.webjars)
    implementation(libs.bundles.spring.jdbc.h2)
    implementation(libs.spring.security.oauth2.authorization.server)
    testImplementation(libs.bundles.spring.test)
}
