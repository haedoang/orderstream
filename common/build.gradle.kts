plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    api("org.springframework.kafka:spring-kafka")
    testImplementation(kotlin("test"))
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
