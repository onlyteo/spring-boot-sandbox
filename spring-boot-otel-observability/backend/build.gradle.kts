import java.time.Instant

val jvmMajorVersion: String by project
val jvmVersion = JavaVersion.toVersion(jvmMajorVersion)
val agents by configurations.creating
val opentelemetryJavaAgent =
    "${libs.opentelemetry.java.agent.get().name}-${libs.opentelemetry.java.agent.get().version}.jar"

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencies)
    alias(libs.plugins.google.cloud.jib)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.bundles.spring.web)
    implementation(libs.bundles.spring.micrometer.opentelemetry)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.webjars)
    testImplementation(libs.bundles.spring.test)

    agents(libs.opentelemetry.java.agent)
}

application {
    mainClass = "com.onlyteo.sandbox.OtelObservabilityBackendApplicationKt"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jvmVersion.toString()))
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
    }
}

tasks.register<Copy>("copyAgents") {
    from(agents)
    into("${layout.buildDirectory.get()}/agents")
}

tasks.jib.configure {
    dependsOn("copyAgents")
}
tasks.jibDockerBuild.configure {
    dependsOn("copyAgents")
}
tasks.jibBuildTar.configure {
    dependsOn("copyAgents")
}

jib {
    from {
        image = "eclipse-temurin:${jvmVersion}-jre-alpine"
    }
    to {
        image = "sandbox.spring-boot-otel-observability-${project.name}:latest"
    }
    container {
        appRoot = "/app" // Define app root dir to be able to use it later on
        mainClass = application.mainClass.get()
        jvmFlags = listOf(
            "-Xms256m",
            "-Xmx512m",
            "-javaagent:${jib.container.appRoot}/agents/${opentelemetryJavaAgent}"
        )
        ports = listOf("8080")
        creationTime.set(Instant.now().toString())
        extraDirectories {
            paths {
                path {
                    setFrom("${layout.buildDirectory.get()}/agents")
                    into = "${jib.container.appRoot}/agents"
                }
            }
        }
    }
}
