import org.jooq.meta.jaxb.Logging

plugins {
    id("nu.studer.jooq") version "8.2"
}

dependencies {
    implementation(project(":common"))
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-jooq")

    jooqGenerator("com.mysql:mysql-connector-j")
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
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = "jdbc:mysql://localhost:3309/orderstream"
                    user = "root"
                    password = "root"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "orderstream"
                        includes = "stock"
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

