plugins {
    `java-library`
}

dependencies {
    api(project(":quickstart-domain"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
