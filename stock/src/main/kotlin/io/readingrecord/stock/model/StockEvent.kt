package io.readingrecord.stock.model

import java.time.LocalDateTime

data class StockEvent(
    val productId: Long,
    val orderId: String,
    val quantity: Int,
    val status: StockStatus,
    val availableQuantity: Int,
    val message: String = "",
    val timestamp: LocalDateTime = LocalDateTime.now()
)

enum class StockStatus {
    RESERVED,     // Stock reservation completed (STOCK_RESERVED)
    FAILED        // Insufficient stock (STOCK_FAILED)
}