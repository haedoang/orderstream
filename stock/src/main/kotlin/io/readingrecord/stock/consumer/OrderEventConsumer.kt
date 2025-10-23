package io.readingrecord.stock.consumer

import io.readingrecord.stock.model.OrderEvent
import io.readingrecord.stock.model.OrderStatus
import io.readingrecord.stock.service.StockService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class OrderEventConsumer(
    private val stockService: StockService
) {
    private val logger = LoggerFactory.getLogger(OrderEventConsumer::class.java)

    @KafkaListener(
        topics = ["order-events"],
        groupId = "stock-service",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handleOrderEvent(
        @Payload orderEvent: OrderEvent,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        acknowledgment: Acknowledgment
    ) {
        logger.info("Received order event: {}", orderEvent)
        logger.debug("Received info - Topic: {}, Partition: {}, Offset: {}", topic, partition, offset)

        try {
            when (orderEvent.status) {
                OrderStatus.CREATED -> {
                    logger.info("Started processing order creation event - checking stock")
                    stockService.checkAndReserveStock(orderEvent.orderId, orderEvent.productId, orderEvent.quantity)
                }
                else -> {
                    logger.warn("Unhandled order status: {}", orderEvent.status)
                }
            }

            acknowledgment.acknowledge()
            logger.info("Order event processing completed: {}", orderEvent.orderId)

        } catch (e: Exception) {
            logger.error("Failed to process order event: {}, Error: {}", orderEvent, e.message, e)
            // Acknowledge even on failure to prevent infinite reprocessing (consider DLQ in production)
            acknowledgment.acknowledge()
        }
    }
}