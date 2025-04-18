
// PROJECT
rootProject.name = "spring-boot-sandbox"

// MANAGEMENT
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// PLUGINS
plugins {
    kotlin("jvm") version "2.1.20" apply false
    kotlin("plugin.spring") version "2.1.20" apply false
    kotlin("plugin.serialization") version "2.1.20" apply false
    id("org.springframework.boot") version "3.4.4" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.google.cloud.tools.jib") version "3.4.5" apply false
    id("org.jooq.jooq-codegen-gradle") version "3.20.1" apply false
    id("com.gorylenko.gradle-git-properties") version "2.5.0" apply false
    id("com.expediagroup.graphql") version "8.4.0" apply false
}

// LIBS


// APPS
include(":apps:spring-boot-rest-api:frontend")
include(":apps:spring-boot-rest-api:frontend-api")
include(":apps:spring-boot-rest-api:backend")

include(":apps:spring-boot-oauth2-token-relay:frontend")
include(":apps:spring-boot-oauth2-token-relay:frontend-api")
include(":apps:spring-boot-oauth2-token-relay:backend")
include(":apps:spring-boot-oauth2-token-relay:authorization-server")

include(":apps:spring-boot-oauth2-token-exchange:frontend")
include(":apps:spring-boot-oauth2-token-exchange:frontend-api")
include(":apps:spring-boot-oauth2-token-exchange:backend")
include(":apps:spring-boot-oauth2-token-exchange:authorization-server")

include(":apps:spring-boot-oauth2-extended-authorization-server:frontend")
include(":apps:spring-boot-oauth2-extended-authorization-server:backend")
include(":apps:spring-boot-oauth2-extended-authorization-server:authorization-server")

include(":apps:spring-boot-cqrs-kafka:frontend")
include(":apps:spring-boot-cqrs-kafka:frontend-api")
include(":apps:spring-boot-cqrs-kafka:backend")

include(":apps:spring-boot-cdc-kafka:frontend")
include(":apps:spring-boot-cdc-kafka:frontend-api")
include(":apps:spring-boot-cdc-kafka:backend")

include(":apps:spring-boot-otel-observability:frontend")
include(":apps:spring-boot-otel-observability:frontend-api")
include(":apps:spring-boot-otel-observability:backend")
