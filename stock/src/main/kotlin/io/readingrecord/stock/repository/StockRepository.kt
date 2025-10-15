package io.readingrecord.stock.repository

import io.readingrecord.stock.entity.Stock
import io.readingrecord.stock.jooq.tables.Stock.STOCK
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class StockRepository(
    private val dslContext: DSLContext
) {

    /**
     * Find stock information by product ID
     */
    fun findByProductId(productId: Long): Stock? {
        return dslContext.selectFrom(STOCK)
            .where(STOCK.PRODUCT_ID.eq(productId))
            .fetchOne()?.let { record ->
                Stock(
                    productId = record.productId,
                    quantity = record.quantity,
                    reservedQuantity = record.reservedQuantity,
                    createdAt = record.createdAt,
                    updatedAt = record.updatedAt
                )
            }
    }


    /**
     * Reserve stock (with concurrency control)
     * @param productId Product ID
     * @param requestQuantity Quantity to reserve
     * @return Whether reservation was successful
     */
    fun reserveStock(productId: Long, requestQuantity: Int): Boolean {
        val affectedRows = dslContext.update(STOCK)
            .set(STOCK.RESERVED_QUANTITY, STOCK.RESERVED_QUANTITY.plus(requestQuantity))
            .where(STOCK.PRODUCT_ID.eq(productId)
                .and(STOCK.QUANTITY.minus(STOCK.RESERVED_QUANTITY).ge(requestQuantity)))
            .execute()

        return affectedRows > 0
    }

}