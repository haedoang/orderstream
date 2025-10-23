package io.readingrecord.stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["io.readingrecord"])
class StockApplication {

}

fun main(args: Array<String>) {
    runApplication<StockApplication>(*args)
}
