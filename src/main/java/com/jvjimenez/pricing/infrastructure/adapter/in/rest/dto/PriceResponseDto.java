package com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto;

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
}
