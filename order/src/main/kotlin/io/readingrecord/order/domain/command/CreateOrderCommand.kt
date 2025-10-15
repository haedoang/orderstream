package io.readingrecord.order.domain.command

import java.math.BigDecimal

data class CreateOrderCommand(
    val customerId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal
) {
    init {
        require(customerId > 0) { "고객 ID는 0보다 커야 합니다." }
        require(productId > 0) { "상품 ID는 0보다 커야 합니다." }
        require(quantity > 0) { "수량은 0보다 커야 합니다." }
        require(unitPrice > BigDecimal.ZERO) { "단가는 0보다 커야 합니다." }
    }

    fun calculateTotalAmount(): BigDecimal = unitPrice.multiply(BigDecimal.valueOf(quantity.toLong()))
}