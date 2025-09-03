dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    // JOOQ
    implementation("org.jooq:jooq")
    implementation("org.jooq:jooq-codegen")
    implementation("org.jooq:jooq-meta")

    // h2
    implementation("com.h2database:h2")

    testImplementation(kotlin("test"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("io.readingrecord.order.OrderApplicationKt")
}
