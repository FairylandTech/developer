plugins {
    `java-library`
}

dependencies {
    implementation(project(":quickstart-facade"))
    implementation(project(":quickstart-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}
