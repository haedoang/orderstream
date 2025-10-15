package io.readingrecord.order.application.port.`in`

import io.readingrecord.order.domain.command.CreateOrderCommand
import io.readingrecord.order.domain.command.UpdateOrderStatusCommand
import io.readingrecord.order.domain.model.Order

interface OrderUseCase {
    fun createOrder(command: CreateOrderCommand): Order
    fun getOrder(orderId: Long): Order
    fun updateOrderStatus(command: UpdateOrderStatusCommand)
}
