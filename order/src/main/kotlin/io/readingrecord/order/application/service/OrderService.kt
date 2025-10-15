package io.readingrecord.order.application.service

import io.readingrecord.order.application.exception.OrderNotFoundException
import io.readingrecord.order.application.exception.OrderUpdateFailedException
import io.readingrecord.order.application.port.`in`.OrderUseCase
import io.readingrecord.order.application.port.out.OrderEventPublisher
import io.readingrecord.order.application.port.out.OrderPersistencePort
import io.readingrecord.order.domain.command.PlaceOrderCommand
import io.readingrecord.order.domain.command.UpdateOrderStatusCommand
import io.readingrecord.order.domain.model.Order
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(
    private val orderPersistencePort: OrderPersistencePort,
    private val orderEventPublisher: OrderEventPublisher
) : OrderUseCase {

    override fun placeOrder(command: PlaceOrderCommand): Order {
        val order = command.toPlaceOrder()

        val savedOrder = orderPersistencePort.save(order)

        val orderPlacedEvent = savedOrder.toOrderPlacedEvent()
        orderEventPublisher.publishOrderPlacedEvent(orderPlacedEvent)
        return savedOrder
    }

    @Transactional(readOnly = true)
    override fun getOrder(orderId: Long): Order {
        val savedOrder = orderPersistencePort.findById(orderId)
            ?: throw OrderNotFoundException("주문을 찾을 수 없습니다. orderId: ${orderId}")

        return savedOrder
    }

    override fun updateOrderStatus(command: UpdateOrderStatusCommand) {
        orderPersistencePort.findById(command.orderId)
            ?: throw OrderNotFoundException("주문을 찾을 수 없습니다. orderId: ${command.orderId}")

        val success = orderPersistencePort.updateStatus(command.orderId, command.newStatus)

        if (!success) {
            throw OrderUpdateFailedException("주문 상태 업데이트에 실패했습니다. orderId: ${command.orderId}")
        }

        //TODO 주문 상태 변경 이벤트 발행
    }
}
