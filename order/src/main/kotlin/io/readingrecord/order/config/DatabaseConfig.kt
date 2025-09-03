package io.readingrecord.order.config

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader

@Configuration
class DatabaseConfig {

    @Autowired
    private lateinit var dslContext: DSLContext

    @EventListener(ApplicationReadyEvent::class)
    fun initDatabase() {
        try {
            val resource = ClassPathResource("init.sql")
            val sql = BufferedReader(InputStreamReader(resource.inputStream)).use { reader ->
                reader.readText()
            }
            
            val statements = sql.split(";")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
            
            statements.forEach { statement ->
                dslContext.execute(statement)
            }
            
            println("Order table initialized successfully")
        } catch (e: Exception) {
            println("Failed to initialize Order table: ${e.message}")
            e.printStackTrace()
        }
    }
}