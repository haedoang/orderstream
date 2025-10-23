package io.readingrecord.stock.producer

import io.readingrecord.stock.model.StockEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class StockEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val logger = LoggerFactory.getLogger(StockEventProducer::class.java)

    companion object {
        const val STOCK_EVENTS_TOPIC = "stock-events"
    }

    fun publishStockEvent(stockEvent: StockEvent) {
        logger.info("Preparing to publish stock event: {}", stockEvent)

        val future: CompletableFuture<SendResult<String, Any>> = kafkaTemplate.send(
            STOCK_EVENTS_TOPIC,
            stockEvent.productId.toString(),
            stockEvent
        )

        future.whenComplete { result, exception ->
            if (exception == null) {
                logger.info(
                    "Stock event published successfully: Topic={}, Partition={}, Offset={}, Key={}",
                    result.recordMetadata.topic(),
                    result.recordMetadata.partition(),
                    result.recordMetadata.offset(),
                    stockEvent.productId
                )
            } else {
                logger.error(
                    "Failed to publish stock event: Key={}, Event={}, Error={}",
                    stockEvent.productId,
                    stockEvent,
                    exception.message,
                    exception
                )
            }
        }
    }

}