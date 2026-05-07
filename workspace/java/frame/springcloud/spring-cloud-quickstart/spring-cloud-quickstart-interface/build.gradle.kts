plugins {
    java
}

dependencies {
    implementation(project(":spring-cloud-quickstart-facade"))
    implementation(project(":spring-cloud-quickstart-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}
