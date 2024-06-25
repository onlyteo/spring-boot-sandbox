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

include(":spring-boot-oauth2-client-react:frontend")
include(":spring-boot-oauth2-client-react:frontend-api")
include(":spring-boot-oauth2-client-react:backend")
include(":spring-boot-oauth2-client-react:authorization-server")

include(":spring-boot-oauth2-authorization-server-extended:frontend")
include(":spring-boot-oauth2-authorization-server-extended:authorization-server")

include(":spring-boot-cdc-event-streaming:mastodon-consumer")
include(":spring-boot-cdc-event-streaming:kafka-streams")
