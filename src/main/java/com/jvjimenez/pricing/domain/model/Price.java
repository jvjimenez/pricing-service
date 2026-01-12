package com.jvjimenez.pricing.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Price(Long brandId, Long productId, Instant startDate, Instant endDate, Long applicableRate,
                    Integer priority, BigDecimal price, String curr) {
}
