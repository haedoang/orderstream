# Order Service

Order Service는 주문 생성, 주문 상태 관리 API 개발 및 DB 연동을 담당하는 마이크로서비스입니다.

## 기능 및 작업 목록 (Feature Branches)

### feature/ORD-1: 주문 서비스 기본 설정 
- Spring Boot 프로젝트 설정
- 데이터베이스 연결 설정
- 기본 API 구조 설계
- 예외 처리 공통 모듈 구현

### feature/ORD-2: 주문 생성 API 구현
- 주문 생성 엔드포인트 개발
- 주문 데이터 유효성 검증
- 주문 정보 저장 로직 구현
- 주문 생성 테스트 코드 작성

### feature/ORD-3: 주문 조회 API 구현
- 단일 주문 조회 엔드포인트 개발
- 주문 목록 조회 엔드포인트 개발 (페이징, 필터링)
- 주문 상태별 조회 기능 구현
- 주문 조회 테스트 코드 작성

### feature/ORD-4: 주문 상태 관리 구현
- 주문 상태 변경 엔드포인트 개발
- 상태 변경 유효성 검증 로직 구현
- 주문 상태 이력 관리 기능
- 주문 상태 관리 테스트 코드 작성

### feature/ORD-5: Kafka 이벤트 발행 구현
- 주문 생성 이벤트 발행 구현
- 주문 상태 변경 이벤트 발행 구현
- 이벤트 스키마 정의 및 구현
- 이벤트 발행 테스트 코드 작성

### feature/ORD-6: Kafka 이벤트 구독 구현
- 재고 상태 이벤트 구독 구현
- 배송 상태 이벤트 구독 구현
- 반품 이벤트 구독 구현
- 이벤트 처리 로직 및 테스트 코드 작성

### feature/ORD-7: 주문 취소/변경 기능 구현
- 주문 취소 엔드포인트 개발
- 주문 변경 엔드포인트 개발
- 취소/변경 유효성 검증 로직 구현
- 취소/변경 테스트 코드 작성

### feature/ORD-8: 주문 결제 연동 구현
- 결제 서비스 연동 인터페이스 개발
- 결제 상태 관리 로직 구현
- 결제 실패 처리 로직 구현
- 결제 연동 테스트 코드 작성

### feature/ORD-9: 성능 최적화 및 캐싱
- 주문 조회 성능 최적화
- 캐싱 전략 구현
- 대용량 트래픽 처리 개선
- 성능 테스트 및 모니터링 구현

### feature/ORD-10: 문서화 및 API 테스트
- Swagger/OpenAPI 문서 구현
- API 테스트 자동화
- 사용자 매뉴얼 작성
- 운영 가이드 작성

## API 엔드포인트

```shell
# 주문 생성
POST /api/v1/orders

# 주문 조회
GET /api/v1/orders/{orderId}
GET /api/v1/orders?status={status}&page={page}&size={size}

# 주문 상태 변경
PUT /api/v1/orders/{orderId}/status

# 주문 취소
DELETE /api/v1/orders/{orderId}

# 주문 변경
PUT /api/v1/orders/{orderId}
```

## 로컬 개발 환경 실행

```shell
# 서비스 실행
./gradlew :order:bootRun

# API 테스트
curl localhost:9001/api/v1/orders
```
