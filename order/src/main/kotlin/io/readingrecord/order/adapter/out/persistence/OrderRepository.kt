package io.readingrecord.order.adapter.out.persistence

import io.readingrecord.order.application.port.out.OrderPersistencePort
import io.readingrecord.order.domain.model.Order
import io.readingrecord.order.domain.model.OrderStatus
import io.readingrecord.order.jooq.Tables.TB_ORDER
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.LocalDateTime

@Repository
class OrderRepository(
    private val dsl: DSLContext
) : OrderPersistencePort {

    override fun save(order: Order): Order {
        return if (order.id == null) {
            insertOrder(order)
        } else {
            updateOrder(order)
        }
    }

    private fun insertOrder(order: Order): Order {
        val record = dsl.insertInto(TB_ORDER)
            .set(TB_ORDER.CUSTOMER_ID, order.customerId)
            .set(TB_ORDER.PRODUCT_ID, order.productId)
            .set(TB_ORDER.QUANTITY, order.quantity)
            .set(TB_ORDER.UNIT_PRICE, order.unitPrice)
            .set(TB_ORDER.TOTAL_AMOUNT, order.totalAmount)
            .set(TB_ORDER.STATUS, order.status.name)
            .set(TB_ORDER.CREATED_AT, order.createdAt)
            .set(TB_ORDER.UPDATED_AT, order.updatedAt)
            .returningResult(TB_ORDER.ID)
            .fetchOne()

        val generatedId = record?.get(TB_ORDER.ID)
            ?: throw IllegalArgumentException("주문을 생성할 수 없습니다.")

        return order.copy(id = generatedId)
    }

    private fun updateOrder(order: Order): Order {
        dsl.update(TB_ORDER)
            .set(TB_ORDER.CUSTOMER_ID, order.customerId)
            .set(TB_ORDER.PRODUCT_ID, order.productId)
            .set(TB_ORDER.QUANTITY, order.quantity)
            .set(TB_ORDER.UNIT_PRICE, order.unitPrice)
            .set(TB_ORDER.TOTAL_AMOUNT, order.totalAmount)
            .set(TB_ORDER.STATUS, order.status.name)
            .set(TB_ORDER.UPDATED_AT, LocalDateTime.now())
            .where(TB_ORDER.ID.eq(order.id))
            .execute()

        return order.copy(updatedAt = LocalDateTime.now())
    }

    override fun findById(id: Long): Order? {
        return dsl.select()
            .from(TB_ORDER)
            .where(TB_ORDER.ID.eq(id))
            .fetchOne()
            ?.let { mapToOrder(it) }
    }

    override fun updateStatus(orderId: Long, status: OrderStatus): Boolean {
        val updatedRows = dsl.update(TB_ORDER)
            .set(TB_ORDER.STATUS, status.name)
            .set(TB_ORDER.UPDATED_AT, LocalDateTime.now())
            .where(TB_ORDER.ID.eq(orderId))
            .execute()

        return updatedRows > 0
    }

    private fun mapToOrder(record: Record): Order {
        return Order(
            id = record.get(TB_ORDER.ID),
            customerId = record.get(TB_ORDER.CUSTOMER_ID),
            productId = record.get(TB_ORDER.PRODUCT_ID),
            quantity = record.get(TB_ORDER.QUANTITY),
            unitPrice = record.get(TB_ORDER.UNIT_PRICE),
            totalAmount = record.get(TB_ORDER.TOTAL_AMOUNT),
            status = OrderStatus.valueOf(record.get(TB_ORDER.STATUS)),
            createdAt = convertToLocalDateTime(record.get(TB_ORDER.CREATED_AT)),
            updatedAt = convertToLocalDateTime(record.get(TB_ORDER.UPDATED_AT))
        )
    }

    private fun convertToLocalDateTime(value: Any?): LocalDateTime {
        return when (value) {
            is LocalDateTime -> value
            is Timestamp -> value.toLocalDateTime()
            else -> throw IllegalArgumentException("Cannot convert $value to LocalDateTime")
        }
    }
}
