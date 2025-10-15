package io.readingrecord.order.adapter.`in`.web.advice

import io.readingrecord.order.adapter.`in`.web.dto.ErrorResponse
import io.readingrecord.order.application.exception.InvalidOrderRequestException
import io.readingrecord.order.application.exception.OrderNotFoundException
import io.readingrecord.order.application.exception.OrderUpdateFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OrderControllerAdvice {

    @ExceptionHandler(OrderNotFoundException::class)
    fun handleOrderNotFound(ex: OrderNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse("ORDER_NOT_FOUND", ex.message ?: "주문을 찾을 수 없습니다."))
    }

    @ExceptionHandler(InvalidOrderRequestException::class)
    fun handleInvalidOrderRequest(ex: InvalidOrderRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse("INVALID_ORDER_REQUEST", ex.message ?: "유효하지 않은 주문 요청입니다."))
    }

    @ExceptionHandler(OrderUpdateFailedException::class)
    fun handleOrderUpdateFailed(ex: OrderUpdateFailedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("ORDER_UPDATE_FAILED", ex.message ?: "주문 업데이트에 실패했습니다."))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."))
    }
}
