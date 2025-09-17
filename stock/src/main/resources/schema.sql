-- Stock 테이블 DDL
CREATE TABLE IF NOT EXISTS stock (
    product_id BIGINT NOT NULL PRIMARY KEY COMMENT '상품 ID',
    quantity INT NOT NULL DEFAULT 0 COMMENT '총 재고 수량',
    reserved_quantity INT NOT NULL DEFAULT 0 COMMENT '예약된 재고 수량',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='재고 관리 테이블';

-- 인덱스 생성
CREATE INDEX idx_stock_product_id ON stock(product_id);
CREATE INDEX idx_stock_quantity ON stock(quantity);
CREATE INDEX idx_stock_updated_at ON stock(updated_at);

-- 재고 체크를 위한 뷰
CREATE OR REPLACE VIEW v_available_stock AS
SELECT
    product_id,
    quantity,
    reserved_quantity,
    (quantity - reserved_quantity) AS available_quantity,
    created_at,
    updated_at
FROM stock
WHERE quantity > 0;