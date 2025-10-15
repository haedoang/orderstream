package io.readingrecord.`return`.model

import io.readingrecord.`return`.handler.response.ReturnResponse

class ReturnMapper {

    companion object {
        fun ReturnEntity.toResponse(): ReturnResponse =
            ReturnResponse(
                id = this.id!!,
                orderId = this.orderId,
                returnId = this.externalReturnId?.toLongOrNull(), // String → Long? 변환
                createAt = this.createdAt?.toLocalDate()
            )
    }
}
