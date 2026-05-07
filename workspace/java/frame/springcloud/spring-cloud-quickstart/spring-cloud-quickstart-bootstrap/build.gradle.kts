plugins {
    id("org.springframework.boot")
    java
}

dependencies {
    implementation(project(":spring-cloud-quickstart-interface"))
    implementation(project(":spring-cloud-quickstart-application"))
    implementation(project(":spring-cloud-quickstart-infrastructure"))
    implementation(project(":spring-cloud-quickstart-domain"))

    runtimeOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    workingDir = rootProject.projectDir
}
