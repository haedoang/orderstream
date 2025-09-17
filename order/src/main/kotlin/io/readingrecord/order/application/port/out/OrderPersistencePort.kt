package io.readingrecord.order.application.port.out

import io.readingrecord.order.domain.model.Order
import io.readingrecord.order.domain.model.OrderStatus

interface OrderPersistencePort {
    fun save(order: Order): Order
    fun findById(orderId: Long): Order?
    fun updateStatus(orderId: Long, newStatus: OrderStatus): Boolean
}