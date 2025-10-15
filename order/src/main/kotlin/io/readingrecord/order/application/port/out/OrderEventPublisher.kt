package io.readingrecord.order.application.port.out

import io.readingrecord.order.domain.event.OrderPlacedEvent

interface OrderEventPublisher {
    fun publishOrderPlacedEvent(event: OrderPlacedEvent)
}
