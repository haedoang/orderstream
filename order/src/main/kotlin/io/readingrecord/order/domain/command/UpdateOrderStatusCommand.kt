package io.readingrecord.order.domain.command

import io.readingrecord.order.domain.model.OrderStatus

data class UpdateOrderStatusCommand(
    val orderId: Long,
    val newStatus: OrderStatus
) {
    init {
        require(orderId > 0) { "주문 ID는 0보다 커야 합니다." }
    }
}