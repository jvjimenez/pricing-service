package com.jvjimenez.pricing.domain.port.primary;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceResult(
        Long brandId,
        Long productId,
        Instant startDate,
        Instant endDate,
        Long applicableRate,
        BigDecimal price,
        String curr
) {
}