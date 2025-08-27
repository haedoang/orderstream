dependencies {
    implementation(project(":common"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("io.readingrecord.order.ReturnApplicationKt")
}

