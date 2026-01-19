CREATE TABLE price
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id        BIGINT         NOT NULL,
    product_id      BIGINT         NOT NULL,
    applicable_rate BIGINT         NOT NULL,
    priority        INT            NOT NULL,
    start_date      TIMESTAMP      NOT NULL,
    end_date        TIMESTAMP      NOT NULL,
    price           DECIMAL(10, 2) NOT NULL,
    curr            VARCHAR(3)     NOT NULL
);
CREATE INDEX idx_brand_product_dates_priority
    ON price (brand_id, product_id, start_date, end_date, priority DESC);