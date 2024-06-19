val jvmMajorVersion: String by project
val jvmVersion = JavaVersion.toVersion(jvmMajorVersion)

plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependencies) apply false
    alias(libs.plugins.google.cloud.jib) apply false
    alias(libs.plugins.git.properties) apply false
}

allprojects {
    group = "com.onlyteo.sandbox"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    configurations {
        all {
            exclude(group = "junit", module = "junit")
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }
    }
}
