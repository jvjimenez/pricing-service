package com.jvjimenez.pricing.application.view;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceSummary(
        Long brandId,
        Long productId,
        Instant startDate,
        Instant endDate,
        Long applicableRate,
        BigDecimal price,
        String currency
) {
}