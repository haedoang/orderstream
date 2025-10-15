CREATE TABLE IF NOT EXISTS returns (
  id BIGINT auto_increment PRIMARY KEY,
  order_id BIGINT NOT NULL,
  external_return_id VARCHAR(64),
  status VARCHAR(32) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  version BIGINT,
  UNIQUE KEY uq_external_return (external_return_id),
  KEY idx_returns_order (order_id),
  KEY idx_returns_status (status),
  KEY idx_returns_updated (updated_at)
);

CREATE TABLE IF NOT EXISTS return_items (
  id BIGINT auto_increment PRIMARY KEY,
  return_id BIGINT NOT NULL,
  order_item_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(18,2),
  reason_code VARCHAR(64),
  is_opened BOOLEAN NOT NULL DEFAULT FALSE,
  is_damaged BOOLEAN NOT NULL DEFAULT FALSE,
  KEY idx_return_items_return (return_id)
);
