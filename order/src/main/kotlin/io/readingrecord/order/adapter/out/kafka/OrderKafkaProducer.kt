package io.readingrecord.order.adapter.out.kafka

import io.readingrecord.order.application.port.out.OrderEventPublisher
import io.readingrecord.order.domain.event.OrderPlacedEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class OrderKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : OrderEventPublisher {

    companion object {
        private const val ORDER_PLACED_TOPIC = "order.placed"
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun publishOrderPlacedEvent(event: OrderPlacedEvent) {
        log.info("publishOrderPlacedEvent invoke: $event")
        kafkaTemplate.send(ORDER_PLACED_TOPIC, event.sagaId, event)
    }
}
