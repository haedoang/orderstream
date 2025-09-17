package io.readingrecord.order.adapter.`in`.web.controller

import io.readingrecord.order.adapter.`in`.web.OrderRequestDto
import io.readingrecord.order.adapter.`in`.web.dto.OrderResponseDto
import io.readingrecord.order.application.port.`in`.OrderUseCase
import io.readingrecord.order.domain.command.UpdateOrderStatusCommand
import io.readingrecord.order.domain.model.OrderStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderUseCase: OrderUseCase
) {

    @PostMapping
    fun createOrder(@RequestBody orderRequest: OrderRequestDto): ResponseEntity<OrderResponseDto> {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(OrderResponseDto.from(orderUseCase.createOrder(orderRequest.toCommand())))
    }

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: Long): ResponseEntity<OrderResponseDto> {
        return ResponseEntity.ok(OrderResponseDto.from(orderUseCase.getOrder(orderId)))
    }

    @PatchMapping("/{orderId}/status")
    fun updateOrderStatus(
        @PathVariable orderId: Long,
        @RequestParam status: OrderStatus
    ): ResponseEntity<Void> {
        orderUseCase.updateOrderStatus(UpdateOrderStatusCommand(orderId, status))
        return ResponseEntity.ok().build()
    }
}

