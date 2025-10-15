package io.readingrecord.`return`.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("returns")
data class ReturnEntity(

    @Id
    @Column("id")
    val id: Long? = null,
    // 링크
    @Column("order_id")
    val orderId: Long,

    @Column("external_return_id")
    val externalReturnId: String? = null,

    @Column("status")
    val status: ReturnStatus = ReturnStatus.REQUESTED,

    @CreatedDate
    @Column("created_at")
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column("updated_at")
    val updatedAt: LocalDateTime? = null,

    @Version
    @Column("version")
    val version: Long? = null
)

enum class ReturnStatus {
    REQUESTED, APPROVED, IN_TRANSIT, RECEIVED, INSPECTED, REFUNDED, REJECTED, CANCELED
}


