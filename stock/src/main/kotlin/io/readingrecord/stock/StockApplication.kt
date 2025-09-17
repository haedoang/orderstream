package io.readingrecord.stock

import io.readingrecord.common.component.SlackComponent
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(scanBasePackages = ["io.readingrecord"])
class StockApplication {

    @RestController
    class IndexController(private val applicationContext: ApplicationContext) {

        @GetMapping
        fun index() = "%s is running!".format(applicationContext.id)
    }

    @Bean
    fun applicationStartup(slackComponent: SlackComponent) = ApplicationRunner {
        try {
            slackComponent.sendMessage("🚀 Stock Service가 시작되었습니다!", "C09D8SDSLE9")
            println("Slack 테스트 메시지 전송 완료")
        } catch (error: Exception) {
            println("Slack 테스트 메시지 전송 실패: ${error.message}")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<StockApplication>(*args)
}
