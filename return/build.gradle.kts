dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    implementation("io.projectreactor.kafka:reactor-kafka")

    implementation("io.asyncer:r2dbc-mysql:1.3.2")

    implementation("io.r2dbc:r2dbc-pool")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

configurations {
    implementation {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
    }
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("io.readingrecord.order.ReturnApplicationKt")
}
