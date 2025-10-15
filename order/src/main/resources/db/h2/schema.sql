DROP TABLE IF EXISTS TB_ORDER;

CREATE TABLE TB_ORDER (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        customer_id BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        quantity INT NOT NULL,
                        unit_price DECIMAL(10, 2) NOT NULL,
                        total_amount DECIMAL(10, 2) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

