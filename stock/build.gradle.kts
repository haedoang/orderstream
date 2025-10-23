import org.jooq.meta.jaxb.Logging

plugins {
    id("nu.studer.jooq") version "8.2"
}

dependencies {
    implementation(project(":common"))
    implementation("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-web")

    jooqGenerator("com.h2database:h2")
}

sourceSets {
    main {
        java {
            srcDir("build/generated-src/jooq")
        }
    }
}

jooq {
    version.set("3.18.7")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.h2.Driver"
                    url = "jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM 'src/main/resources/schema.sql'"
                    user = "sa"
                    password = ""
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.h2.H2Database"
                        inputSchema = "PUBLIC"
                        includes = "STOCK"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                        isKotlinSetterJvmNameAnnotationsOnIsPrefix = true
                    }
                    target.apply {
                        packageName = "io.readingrecord.stock.jooq"
                        directory = "build/generated-src/jooq"
                    }
                    strategy.apply {
                        name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    }
                }
            }
        }
    }
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("io.readingrecord.stock.StockApplicationKt")
}

