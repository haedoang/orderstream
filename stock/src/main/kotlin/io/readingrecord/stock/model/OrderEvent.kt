package io.readingrecord.stock.model

import java.time.LocalDateTime

data class OrderEvent(
    val orderId: String,
    val productId: Long,
    val quantity: Int,
    val status: OrderStatus,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

enum class OrderStatus {
    CREATED,      // Order created
    CONFIRMED,    // Order confirmed
    CANCELLED     // Order cancelled
}