package io.readingrecord.order

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication(scanBasePackages = ["io.readingrecord"])
class OrderApplication {

    @Bean
    fun init(kafkaTemplate: KafkaTemplate<String, Any>): CommandLineRunner {
        return CommandLineRunner {
            println(kafkaTemplate)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}

