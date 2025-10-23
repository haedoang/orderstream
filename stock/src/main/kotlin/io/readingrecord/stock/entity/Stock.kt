package io.readingrecord.stock.entity

import java.time.LocalDateTime

data class Stock(
    val productId: Long,
    val quantity: Int,
    val reservedQuantity: Int = 0,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    fun canReserve(requestQuantity: Int): Boolean {
        return (quantity - reservedQuantity) >= requestQuantity
    }

    fun getAvailableQuantity(): Int {
        return quantity - reservedQuantity
    }

}