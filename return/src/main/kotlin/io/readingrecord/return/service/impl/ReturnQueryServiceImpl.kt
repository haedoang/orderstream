package io.readingrecord.`return`.service.impl

import io.readingrecord.`return`.handler.response.ReturnResponse
import io.readingrecord.`return`.model.ReturnMapper.Companion.toResponse
import io.readingrecord.`return`.repository.ReturnRepository
import io.readingrecord.`return`.service.ReturnQueryService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReturnQueryServiceImpl(
    private val returnRepository: ReturnRepository
) : ReturnQueryService {

    override fun getById(id: Long): Mono<ReturnResponse> =
        returnRepository.findById(id)
            .switchIfEmpty(Mono.error(ReturnNotFoundException(id)))
            .flatMap { e ->
                // id가 null인 비정상 레코드 방어
                if (e.id == null) {
                    Mono.error(ReturnNotFoundException(id))
                }
                else{
                    Mono.just(e.toResponse())
                }
            }

    class ReturnNotFoundException(id: Long) : RuntimeException("Return not found: $id")
}
