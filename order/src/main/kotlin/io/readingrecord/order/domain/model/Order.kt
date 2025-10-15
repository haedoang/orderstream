package io.readingrecord.order.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val id: Long? = null,
    val customerId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
