plugins {
    kotlin("jvm") version "2.2.20"
    java
    antlr
}

group = "com.gr72s"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/central/")
    }
    mavenCentral()
}

dependencies {
    antlr(libs.antlr4)
    implementation(libs.antlr4.runtime)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.generateGrammarSource<AntlrTask> {
    val pkg = "com.gr72s"
    val targetSourceDir = file("src/main/antlr/.antlr")
    with(arguments) {
        addAll(listOf("-package", pkg, "-visitor", "-no-listener"))
    }
    outputDirectory = outputDirectory.resolve(pkg.split(".").joinToString("/"))
}