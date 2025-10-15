package io.readingrecord.`return`.handler.response

import java.time.LocalDate


data class ReturnResponse(
    val id: Long,
    val orderId: Long?,
    val returnId: Long?,
    val createAt: LocalDate?
)

