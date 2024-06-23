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

include(":spring-boot-oauth2-react-client:frontend")
include(":spring-boot-oauth2-react-client:frontend-api")
include(":spring-boot-oauth2-react-client:backend")
include(":spring-boot-oauth2-react-client:authorization-server")

include(":spring-boot-oauth2-authorization-server-extended:frontend")
include(":spring-boot-oauth2-authorization-server-extended:authorization-server")

include(":spring-boot-cdc-event-streaming:kafka-streams-consumer")
include(":spring-boot-cdc-event-streaming:mastodon-consumer")
