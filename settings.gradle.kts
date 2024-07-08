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
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

rootProject.name = "spring-boot-sandbox"

include(":spring-boot-oauth2-token-relay:frontend")
include(":spring-boot-oauth2-token-relay:frontend-api")
include(":spring-boot-oauth2-token-relay:backend")
include(":spring-boot-oauth2-token-relay:authorization-server")

include(":spring-boot-oauth2-extended-authorization-server:frontend")
include(":spring-boot-oauth2-extended-authorization-server:backend")
include(":spring-boot-oauth2-extended-authorization-server:authorization-server")

include(":spring-boot-cdc-event-streaming:mastodon-consumer")
include(":spring-boot-cdc-event-streaming:kafka-streams")
