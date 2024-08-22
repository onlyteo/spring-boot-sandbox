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

rootProject.name = "spring-boot-sandbox"

include(":spring-boot-rest-api:frontend")
include(":spring-boot-rest-api:frontend-api")
include(":spring-boot-rest-api:backend")

include(":spring-boot-oauth2-token-relay:frontend")
include(":spring-boot-oauth2-token-relay:frontend-api")
include(":spring-boot-oauth2-token-relay:backend")
include(":spring-boot-oauth2-token-relay:authorization-server")

include(":spring-boot-oauth2-token-exchange:frontend")
include(":spring-boot-oauth2-token-exchange:frontend-api")
include(":spring-boot-oauth2-token-exchange:backend")
include(":spring-boot-oauth2-token-exchange:authorization-server")

include(":spring-boot-oauth2-extended-authorization-server:frontend")
include(":spring-boot-oauth2-extended-authorization-server:backend")
include(":spring-boot-oauth2-extended-authorization-server:authorization-server")

include(":spring-boot-kafka-cqrs:frontend")
include(":spring-boot-kafka-cqrs:frontend-api")
include(":spring-boot-kafka-cqrs:backend")

include(":spring-boot-kafka-cdc:frontend")
include(":spring-boot-kafka-cdc:frontend-api")
include(":spring-boot-kafka-cdc:backend")

include(":spring-boot-otel-observability:frontend")
include(":spring-boot-otel-observability:frontend-api")
include(":spring-boot-otel-observability:backend")
