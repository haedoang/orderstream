#!/usr/bin/env kotlin

@file:DependsOn("org.jooq:jooq-codegen:3.18.7")
@file:DependsOn("com.h2database:h2:2.2.224")

import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import java.io.File
import java.sql.DriverManager

/**
 * JOOQ 코드 생성 스크립트
 *
 * 사용법:
 * ./scripts/jooq-generator.kt
 */

fun main() {
    println("🚀 JOOQ 코드 생성을 시작합니다...")

    val projectDir = File(System.getProperty("user.dir"))
    val schemaFile = File(projectDir, "src/main/resources/schema.sql")
    val generatedDir = File(projectDir, "src/main/generated")
    val dbFile = File(projectDir, "build/tmp/jooq-db")

    // 디렉토리 정리 및 생성
    println("📁 디렉토리를 준비합니다...")
    generatedDir.deleteRecursively()
    generatedDir.mkdirs()
    dbFile.parentFile.mkdirs()

    // H2 데이터베이스 연결 및 스키마 생성
    val jdbcUrl = "jdbc:h2:${dbFile.absolutePath};MODE=MySQL;DATABASE_TO_LOWER=TRUE"
    println("🗄️  H2 데이터베이스에 연결합니다: $jdbcUrl")

    DriverManager.getConnection(jdbcUrl, "sa", "").use { connection ->
        // 스키마 실행
        if (schemaFile.exists()) {
            println("📋 스키마를 실행합니다: ${schemaFile.name}")
            val schema = schemaFile.readText()
            connection.createStatement().use { statement ->
                statement.execute(schema)
            }
        } else {
            println("⚠️  스키마 파일을 찾을 수 없습니다: ${schemaFile.absolutePath}")
        }

        // 테이블 목록 확인
        val tables = mutableListOf<String>()
        connection.metaData.getTables(null, "PUBLIC", null, arrayOf("TABLE")).use { rs ->
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"))
            }
        }
        println("📊 발견된 테이블: ${tables.joinToString(", ")}")
    }

    // JOOQ 설정
    val configuration = Configuration().apply {
        jdbc = Jdbc().apply {
            driver = "org.h2.Driver"
            url = jdbcUrl
            user = "sa"
            password = ""
        }

        generator = Generator().apply {
            name = "org.jooq.codegen.KotlinGenerator"

            database = Database().apply {
                name = "org.jooq.meta.h2.H2Database"
                inputSchema = "PUBLIC"
                includes = ".*"
                excludes = ""
            }

            generate = Generate().apply {
                isDeprecated = false
                isRecords = true
                isImmutablePojos = false
                isFluentSetters = true
                isDaos = false
                isJavaTimeTypes = true
                isKotlinSetterJvmNameAnnotationsOnIsPrefix = true
                isKotlinNotNullPojoTypes = true
                isKotlinNotNullRecordTypes = true
                isKotlinNotNullInterfaceTypes = true
            }

            target = Target().apply {
                packageName = "io.readingrecord.order.jooq"
                directory = generatedDir.absolutePath
            }

            strategy = Strategy().apply {
                name = "org.jooq.codegen.DefaultGeneratorStrategy"
            }
        }
    }

    try {
        println("⚙️  JOOQ 코드를 생성합니다...")
        GenerationTool.generate(configuration)

        // 생성 결과 확인
        val tablesDir = File(generatedDir, "io/readingrecord/order/jooq/tables")
        if (tablesDir.exists() && tablesDir.listFiles()?.isNotEmpty() == true) {
            println("✅ JOOQ 코드 생성이 완료되었습니다!")
            println("📁 생성된 파일들:")
            tablesDir.listFiles()?.forEach { file ->
                println("   - ${file.name}")
            }
        } else {
            println("❌ JOOQ 코드 생성에 실패했습니다.")
        }

    } catch (e: Exception) {
        println("❌ 오류 발생: ${e.message}")
        e.printStackTrace()
    }

    // 정리
    dbFile.delete()
    println("🎉 JOOQ 코드 생성 프로세스가 완료되었습니다!")
}