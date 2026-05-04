plugins {
    `java-library`
}

dependencies {
    implementation(project(":quickstart-domain"))
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
