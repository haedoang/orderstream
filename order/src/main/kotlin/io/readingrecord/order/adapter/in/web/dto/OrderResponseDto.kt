package io.readingrecord.order.adapter.`in`.web.dto

import io.readingrecord.order.domain.model.Order
import io.readingrecord.order.domain.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponseDto(
    val id: Long,
    val customerId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val statusMessage: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(order: Order): OrderResponseDto {
            return OrderResponseDto(
                id = order.id!!,
                customerId = order.customerId,
                productId = order.productId,
                quantity = order.quantity,
                unitPrice = order.unitPrice,
                totalAmount = order.totalAmount,
                status = order.status,
                statusMessage = order.status.getStatusText(),
                createdAt = order.createdAt,
                updatedAt = order.updatedAt
            )
        }
    }
}
