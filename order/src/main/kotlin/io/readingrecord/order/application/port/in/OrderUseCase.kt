package io.readingrecord.order.application.port.`in`

import io.readingrecord.order.domain.command.PlaceOrderCommand
import io.readingrecord.order.domain.command.UpdateOrderStatusCommand
import io.readingrecord.order.domain.model.Order

interface OrderUseCase {
    fun placeOrder(command: PlaceOrderCommand): Order
    fun getOrder(orderId: Long): Order
    fun updateOrderStatus(command: UpdateOrderStatusCommand)
}
