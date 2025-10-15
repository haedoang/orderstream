package io.readingrecord.order.domain.model

import io.readingrecord.order.domain.event.OrderPlacedEvent
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Order(
    val id: Long? = null,
    val sagaId: String = UUID.randomUUID().toString(),
    val customerId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun toOrderPlacedEvent(): OrderPlacedEvent {
        require(id != null) { "주문 ID가 없습니다. 저장된 주문만 이벤트를 발행할 수 있습니다." }

        return OrderPlacedEvent(
            sagaId = sagaId,
            orderId = id,
            customerId = customerId,
            productId = productId,
            quantity = quantity,
            totalAmount = totalAmount
        )
    }
}
