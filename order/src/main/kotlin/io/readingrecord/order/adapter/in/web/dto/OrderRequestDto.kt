package io.readingrecord.order.adapter.`in`.web

import io.readingrecord.order.domain.command.CreateOrderCommand
import io.readingrecord.order.domain.model.Order
import io.readingrecord.order.domain.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderRequestDto(
    val customerId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal
) {
    fun toOrder(): Order {
        return Order(
            customerId = customerId,
            productId = productId,
            quantity = quantity,
            unitPrice = unitPrice,
            totalAmount = unitPrice.multiply(BigDecimal(quantity)),
            status = OrderStatus.ORDER_PLACED,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toCommand(): CreateOrderCommand {
        return CreateOrderCommand(customerId, productId, quantity, unitPrice)
    }
}
