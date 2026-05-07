plugins {
    java
}

dependencies {
    implementation(project(":spring-cloud-quickstart-domain"))

    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:3.5.15")
    implementation("com.baomidou:mybatis-plus-jsqlparser:3.5.15")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    runtimeOnly("com.mysql:mysql-connector-j")  // MySQL JDBC 驱动
    runtimeOnly("org.postgresql:postgresql")  // PostgreSQL JDBC 驱动
}
