-- 데이터베이스 생성 및 사용
USE orderstream;

-- stock 테이블 생성
CREATE TABLE IF NOT EXISTS stock (
    product_id BIGINT PRIMARY KEY,
    quantity INT NOT NULL DEFAULT 0,
    reserved_quantity INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 테스트 데이터 삽입
INSERT INTO stock (product_id, quantity, reserved_quantity) VALUES 
(1001, 100, 0),
(1002, 50, 0),
(1003, 25, 0),
(1004, 0, 0),
(1005, 200, 0);

-- 인덱스 생성
CREATE INDEX idx_stock_product_id ON stock(product_id);

-- 확인용 쿼리
SELECT 'Stock table created and initialized' as status;
SELECT * FROM stock;