package io.readingrecord.`return`.service

import io.readingrecord.`return`.handler.response.ReturnResponse
import reactor.core.publisher.Mono

interface ReturnQueryService {

    fun getById(id: Long): Mono<ReturnResponse>
}
