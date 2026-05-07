plugins {
    `java-library`
}

group = "host.fairy"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":spring-cloud-quickstart-application"))
    implementation(project(":spring-cloud-quickstart-domain"))
    implementation(project(":spring-cloud-quickstart-infrastructure"))

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
