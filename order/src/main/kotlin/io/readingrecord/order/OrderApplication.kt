package io.readingrecord.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["io.readingrecord"])
class OrderApplication

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}

