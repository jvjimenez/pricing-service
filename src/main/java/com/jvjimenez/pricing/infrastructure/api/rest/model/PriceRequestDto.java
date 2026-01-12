package com.jvjimenez.pricing.infrastructure.api.rest.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class PriceRequestDto {

    @NotNull
    @Positive
    private Long brandId;

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    private Instant searchDate;


    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Instant getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Instant searchDate) {
        this.searchDate = searchDate;
    }

}
