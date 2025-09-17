package io.readingrecord.stock

import io.readingrecord.common.component.SlackComponent
import io.readingrecord.stock.repository.StockRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(scanBasePackages = ["io.readingrecord"])
class StockApplication {

    private val logger = LoggerFactory.getLogger(StockApplication::class.java)

    @Bean
    fun stockTestRunner(stockRepository: StockRepository) = ApplicationRunner {
        logger.info("=== Stock Application 시작 - 재고 조회 테스트 ===")

        try {
            val stock = stockRepository.findByProductId(1L)
            if (stock != null) {
                logger.info("✅ 상품 ID 1 재고 조회 성공:")
                logger.info("   - 상품 ID: ${stock.productId}")
                logger.info("   - 총 재고: ${stock.quantity}")
                logger.info("   - 예약 재고: ${stock.reservedQuantity}")
                logger.info("   - 사용가능 재고: ${stock.getAvailableQuantity()}")
                logger.info("   - 품절 여부: ${if (stock.isOutOfStock()) "품절" else "재고있음"}")
                logger.info("   - 생성일시: ${stock.createdAt}")
                logger.info("   - 수정일시: ${stock.updatedAt}")
            } else {
                logger.warn("⚠️ 상품 ID 1 대한 재고 정보를 찾을 수 없습니다.")
            }
        } catch (e: Exception) {
            logger.error("❌ 재고 조회 중 오류 발생: ${e.message}", e)
        }

        logger.info("=== 재고 조회 테스트 완료 ===")
    }

    @RestController
    class IndexController(private val applicationContext: ApplicationContext) {

        @GetMapping
        fun index() = "%s is running!".format(applicationContext.id)
    }

}

fun main(args: Array<String>) {
    runApplication<StockApplication>(*args)
}
