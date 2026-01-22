package com.jvjimenez.pricing.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Price(
        Long brandId,
        Long productId,
        Instant startDate,
        Instant endDate,
        Long applicableRate,
        Integer priority,
        BigDecimal price,
        String currency
) {
    public boolean isApplicableOn(Instant date) {
        return (date.equals(startDate) || date.isAfter(startDate))
                && (date.equals(endDate) || date.isBefore(endDate));
    }
}
