import org.jooq.meta.jaxb.*

plugins {
    id("nu.studer.jooq") version "8.2"
}


dependencies {
    implementation(project(":common"))

    // JOOQ
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.jooq:jooq")
    implementation("org.jooq:jooq-meta")
    implementation("org.jooq:jooq-codegen")

    // h2
    implementation("com.h2database:h2")
    jooqGenerator("org.jooq:jooq-meta-extensions")

    testImplementation(kotlin("test"))
}

jooq {
    version.set("3.18.4")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.h2.Driver"
                    url = "jdbc:h2:mem:orderdb;DB_CLOSE_DELAY=-1"
                    user = "sa"
                    password = ""
                }
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                        properties = listOf(
                            Property().apply {
                                key = "scripts"
                                value = "src/main/resources/db/h2/schema.sql"
                            },
                            Property().apply {
                                key = "sort"
                                value = "semantic"
                            }
                        )
                    }
                    target.apply {
                        packageName = "io.readingrecord.order.jooq"
                        directory = "build/generated-src/jooq"
                    }
                }
            }
        }
    }
}


tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("io.readingrecord.order.OrderApplicationKt")
}
