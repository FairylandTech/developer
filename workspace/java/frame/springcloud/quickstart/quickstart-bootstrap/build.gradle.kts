plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":quickstart-interface"))
    implementation(project(":quickstart-application"))
    implementation(project(":quickstart-infrastructure"))
    implementation(project(":quickstart-domain"))

    runtimeOnly("org.springframework.boot:spring-boot-devtools")
}
