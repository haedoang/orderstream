package io.readingrecord.order.adapter.`in`.web.dto

import io.readingrecord.order.domain.command.PlaceOrderCommand
import java.math.BigDecimal

data class OrderRequestDto(
    val customerId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal
) {

    fun toCommand(): PlaceOrderCommand {
        return PlaceOrderCommand(customerId, productId, quantity, unitPrice)
    }
}
