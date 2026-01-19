package com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto;

import com.jvjimenez.pricing.application.view.PriceSummary;

import java.math.BigDecimal;
import java.time.Instant;


public record PriceResponseDto(
        Long brandId,
        Long productId,
        Long applicableRate,
        BigDecimal price,
        String currency,
        Instant startDate,
        Instant endDate
) {
    public static PriceResponseDto fromPriceSummary(PriceSummary summary) {
        return new PriceResponseDto(
                summary.brandId(),
                summary.productId(),
                summary.applicableRate(),
                summary.price(),
                summary.currency(),
                summary.startDate(),
                summary.endDate()
        );
    }
}