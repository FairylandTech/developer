plugins {
    `java-library`
}

dependencies {
    implementation(project(":quickstart-application"))
    implementation(project(":quickstart-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}
