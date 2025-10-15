package io.readingrecord.order.domain.event

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderPlacedEvent(
    val sagaId: String,
    val orderId: Long,
    val customerId: Long,
    val productId: Long,
    val quantity: Int,
    val totalAmount: BigDecimal,
    val timestamp: LocalDateTime = LocalDateTime.now()
)