plugins {
    id("org.springframework.boot") version "3.1.10" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    java
}

group = "host.fairy"
version = "0.0.1-SNAPSHOT"
description = "quickstart-parent"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

allprojects {
    repositories {
        maven { url = uri("D:/DevelopTools/Repository/maven") }
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    repositories.clear()
    repositories {
        maven {
            url = uri("D:/DevelopTools/Repository/maven")
        }
        mavenCentral()
    }
    group = "host.fairy"
    version = "0.0.1"
    java.sourceCompatibility = JavaVersion.VERSION_17
    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
    dependencies {
        val springBom = platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)

        implementation(springBom)
        implementation("host.fairy:fairylandfuture:1.0.1")

        compileOnly("org.projectlombok:lombok")

        annotationProcessor(springBom)
        annotationProcessor("org.projectlombok:lombok:")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
