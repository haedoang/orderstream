## 이벤트 정의

### 주문 서비스 이벤트
- `OrderCreated`: 주문이 생성되었을 때
- `OrderCancelled`: 주문이 취소되었을 때
- `OrderStatusChanged`: 주문 상태가 변경되었을 때

### 배송 서비스 이벤트
- `DeliveryStarted`: 배송이 시작되었을 때
- `DeliveryCompleted`: 배송이 완료되었을 때

### 재고 서비스 이벤트
- `StockReduced`: 재고가 차감되었을 때
- `StockIncreased`: 재고가 증가되었을 때
- `StockInsufficient`: 재고 부족시

### 반품 서비스 이벤트
- `ReturnRequested`: 반품이 요청되었을 때
- `ReturnCompleted`: 반품이 완료되었을 때

## 모듈별 이벤트 구독/발행 관계

### 주문 서비스 (Order)
**발행:**
- `OrderCreated` → 재고 서비스에서 재고 차감 트리거
- `OrderCancelled` → 재고 서비스에서 재고 복구 트리거
- `OrderStatusChanged` → 다른 서비스들이 주문 상태 변경 감지

**구독:**
- `DeliveryCompleted` → 주문 상태를 '배송완료'로 변경
- `ReturnCompleted` → 주문 상태를 '반품완료'로 변경
- `StockInsufficient` → 주문 실패 처리

### 재고 서비스 (Stock)
**발행:**
- `StockReduced` → 주문/배송 서비스에 재고 차감 알림
- `StockIncreased` → 주문/반품 서비스에 재고 증가 알림
- `StockInsufficient` → 주문 서비스에 재고 부족 알림

**구독:**
- `OrderCreated` → 주문 상품의 재고 차감
- `OrderCancelled` → 취소된 주문의 재고 복구
- `ReturnCompleted` → 반품 완료시 재고 증가

### 배송 서비스 (Delivery)
**발행:**
- `DeliveryStarted` → 주문 서비스에 배송 시작 알림
- `DeliveryCompleted` → 주문 서비스에 배송 완료 알림

**구독:**
- `OrderCreated` → 새 주문에 대한 배송 준비
- `StockReduced` → 재고 확인 후 배송 시작 가능 여부 판단

### 반품 서비스 (Return)
**발행:**
- `ReturnRequested` → 주문/재고 서비스에 반품 요청 알림
- `ReturnCompleted` → 주문/재고 서비스에 반품 완료 알림

**구독:**
- `DeliveryCompleted` → 배송완료된 주문만 반품 가능하도록 제어
- `OrderStatusChanged` → 주문 상태에 따른 반품 가능 여부 판단

## 각 모듈별 구현해야 할 서비스

### 주문 서비스 (Order)
1. **이벤트 발행 서비스**
   - `OrderEventPublisher`: 주문 생성/취소/상태변경 이벤트 발행

2. **이벤트 구독 서비스**
   - `DeliveryEventListener`: 배송 완료 이벤트 구독
   - `ReturnEventListener`: 반품 완료 이벤트 구독
   - `StockEventListener`: 재고 부족 이벤트 구독

### 재고 서비스 (Stock)
1. **이벤트 발행 서비스**
   - `StockEventPublisher`: 재고 증감/부족 이벤트 발행

2. **이벤트 구독 서비스**
   - `OrderEventListener`: 주문 생성/취소 이벤트 구독
   - `ReturnEventListener`: 반품 완료 이벤트 구독

### 배송 서비스 (Delivery)
1. **이벤트 발행 서비스**
   - `DeliveryEventPublisher`: 배송 시작/완료 이벤트 발행

2. **이벤트 구독 서비스**
   - `OrderEventListener`: 주문 생성 이벤트 구독
   - `StockEventListener`: 재고 차감 이벤트 구독

### 반품 서비스 (Return)
1. **이벤트 발행 서비스**
   - `ReturnEventPublisher`: 반품 요청/완료 이벤트 발행

2. **이벤트 구독 서비스**
   - `DeliveryEventListener`: 배송 완료 이벤트 구독
   - `OrderEventListener`: 주문 상태 변경 이벤트 구독

## 공통 추가 사항
1. **이벤트 모델 클래스**: `common` 모듈에 이벤트 데이터 구조 정의
2. **Kafka 의존성**: 각 모듈 `build.gradle.kts`에 추가 필요
3. **Consumer/Producer 설정**: 각 모듈별 Kafka 설정
