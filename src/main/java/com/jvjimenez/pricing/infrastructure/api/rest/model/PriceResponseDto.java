package com.jvjimenez.pricing.infrastructure.api.rest.model;

import java.math.BigDecimal;
import java.time.Instant;

public class PriceResponseDto {

    private Long brandId;
    private Long productId;
    private Long applicableRate;
    private Instant startDate;
    private Instant endDate;
    private BigDecimal price;
    private String curr;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getApplicableRate() {
        return applicableRate;
    }

    public void setApplicableRate(Long applicableRate) {
        this.applicableRate = applicableRate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurr() { return curr; }

    public void setCurr(String curr) { this.curr = curr; }
}
