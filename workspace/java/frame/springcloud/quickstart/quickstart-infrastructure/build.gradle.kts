plugins {
    `java-library`
}

dependencies {
    api(project(":quickstart-domain"))

    implementation("com.baomidou:mybatis-plus-boot-starter")
    runtimeOnly("com.mysql:mysql-connector-j")  // MySQL JDBC 驱动
    runtimeOnly("org.postgresql:postgresql")  // PostgreSQL JDBC 驱动
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
