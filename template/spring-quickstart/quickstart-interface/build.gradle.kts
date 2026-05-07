plugins {
    java
}

dependencies {
    implementation(project(":quickstart-facade"))
    implementation(project(":quickstart-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}
