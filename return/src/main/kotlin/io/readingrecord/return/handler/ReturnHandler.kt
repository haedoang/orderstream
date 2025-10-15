package io.readingrecord.`return`.handler

import io.readingrecord.`return`.service.ReturnQueryService
import io.readingrecord.`return`.service.impl.ReturnQueryServiceImpl
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ReturnHandler(

    private val returnQueryService: ReturnQueryService
) {

    fun getStatus(req: ServerRequest): Mono<ServerResponse> {
        val idStr = req.pathVariable("id")
        val id = idStr.toLongOrNull()
            ?: return ServerResponse.badRequest().bodyValue("Invalid id: $idStr")

        return returnQueryService.getById(id)
            .flatMap { dto ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
            }
            .onErrorResume(ReturnQueryServiceImpl.ReturnNotFoundException::class.java) {
                ServerResponse.notFound().build()
            }
    }
}
