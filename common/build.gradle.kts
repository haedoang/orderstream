import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    api("org.springframework.kafka:spring-kafka")
    testImplementation(kotlin("test"))
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}
