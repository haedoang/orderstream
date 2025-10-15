package io.readingrecord.`return`.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("return_items")
data class ReturnItemEntity(

    @Id
    @Column("id")
    val id: Long? = null,

    @Column("return_id")
    val returnId: Long,

    @Column("order_item_id")
    val orderItemId: Long,

    @Column("quantity")
    val quantity: Int,

    @Column("unit_price")
    val unitPrice: BigDecimal? = null,

    @Column("reason_code")
    val reasonCode: String? = null,

    @Column("is_opened")
    val isOpened: Boolean = false,

    @Column("is_damaged")
    val isDamaged: Boolean = false
)
