pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "tiger-kt"
