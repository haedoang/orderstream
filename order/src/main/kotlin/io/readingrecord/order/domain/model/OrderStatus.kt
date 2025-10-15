package io.readingrecord.order.domain.model

enum class OrderStatus(
    private val displayName: String,
    private val message: String
) {

    // 주문
    ORDER_PLACED("주문 접수", "주문이 접수되었습니다"),
    ORDER_CONFIRMED("주문 확인", "주문이 확인되었습니다"),
    CANCELLED("주문 취소", "주문이 취소되었습니다"),

    // 배송
    SHIPPED("배송 중", "상품이 발송되었습니다"),
    DELIVERED("배송 완료", "상품이 배송되었습니다"),

    // 반품
    RETURN_REQUESTED("반품 신청", "반품이 신청되었습니다"),
    RETURN_COMPLETED("반품 완료", "반품이 완료되었습니다");

    fun getStatusText(): String = "$displayName: $message"
}
