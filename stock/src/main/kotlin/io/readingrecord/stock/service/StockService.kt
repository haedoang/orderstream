package io.readingrecord.stock.service

import io.readingrecord.stock.model.StockEvent
import io.readingrecord.stock.model.StockStatus
import io.readingrecord.stock.producer.StockEventProducer
import io.readingrecord.stock.repository.StockRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StockService(
    private val stockRepository: StockRepository,
    private val stockEventProducer: StockEventProducer
) {
    private val logger = LoggerFactory.getLogger(StockService::class.java)

    /**
     * Check and reserve stock (when order is created)
     */
    fun checkAndReserveStock(orderId: String, productId: Long, quantity: Int) {
        logger.info("Started stock check: order={}, product={}, quantity={}", orderId, productId, quantity)

        val stock = stockRepository.findByProductId(productId)
        if (stock == null) {
            logger.warn("Product not found: {}", productId)
            publishStockEvent(orderId, productId, quantity, StockStatus.FAILED, 0, "Product not found")
            return
        }

        if (!stock.canReserve(quantity)) {
            logger.warn("Insufficient stock: product={}, requested={}, available={}", productId, quantity, stock.getAvailableQuantity())
            publishStockEvent(orderId, productId, quantity, StockStatus.FAILED, stock.getAvailableQuantity(), "Insufficient stock")
            return
        }

        val reserveSuccess = stockRepository.reserveStock(productId, quantity)
        if (reserveSuccess) {
            val updatedStock = stockRepository.findByProductId(productId)!!
            logger.info("Stock reservation successful: product={}, available_after_reservation={}", productId, updatedStock.getAvailableQuantity())
            publishStockEvent(orderId, productId, quantity, StockStatus.RESERVED, updatedStock.getAvailableQuantity(), "Stock reservation completed")
        } else {
            logger.error("Stock reservation failed: product={}", productId)
            publishStockEvent(orderId, productId, quantity, StockStatus.FAILED, stock.getAvailableQuantity(), "Stock reservation failed")
        }
    }

    private fun publishStockEvent(orderId: String, productId: Long, quantity: Int, status: StockStatus, availableQuantity: Int, message: String) {
        val stockEvent = StockEvent(
            productId = productId,
            orderId = orderId,
            quantity = quantity,
            status = status,
            availableQuantity = availableQuantity,
            message = message
        )
        stockEventProducer.publishStockEvent(stockEvent)
    }
}