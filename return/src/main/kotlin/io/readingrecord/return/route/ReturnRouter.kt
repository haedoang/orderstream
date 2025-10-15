package io.readingrecord.`return`.route

import io.readingrecord.`return`.handler.ReturnHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class ReturnRouter(
    private val handler: ReturnHandler
){

    // 반품 요청 이벤트 수신 (api 로 호출시)
    @Bean
    fun routes() = router {
        // 현재 상태 조회
        GET("/{id}", handler::getStatus)
    }
}
