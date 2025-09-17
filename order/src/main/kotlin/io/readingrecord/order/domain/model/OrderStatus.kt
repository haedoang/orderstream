package io.readingrecord.order.domain.model

enum class OrderStatus(
    private val displayName: String,
    private val message: String
) {
    // 주문 단계
    ORDER_PLACED("주문 완료", "주문이 접수되었습니다"),
    ORDER_CONFIRMED("주문 확인", "주문이 확인되었습니다"),

    // 결제 단계
    PAYMENT_PENDING("결제 대기", "결제를 대기 중입니다"),
    PAYMENT_COMPLETED("결제 완료", "결제가 완료되었습니다"),
    PAYMENT_FAILED("결제 실패", "결제에 실패했습니다"),

    // 배송 단계
    PREPARING("배송 준비", "상품을 준비 중입니다"),
    SHIPPED("배송 시작", "상품이 발송되었습니다"),
    DELIVERED("배송 완료", "상품이 배송되었습니다"),

    // 반품 단계
    RETURN_REQUESTED("반품 신청", "반품이 신청되었습니다"),
    RETURN_APPROVED("반품 승인", "반품이 승인되었습니다"),
    RETURN_REJECTED("반품 거부", "반품이 거부되었습니다"),
    RETURN_COMPLETED("반품 완료", "반품이 완료되었습니다"),

    // 완료/취소
    COMPLETED("주문 완료", "모든 처리가 완료되었습니다"),
    CANCELLED("주문 취소", "주문이 취소되었습니다");

    fun getStatusText(): String = "$displayName: $message"
}
