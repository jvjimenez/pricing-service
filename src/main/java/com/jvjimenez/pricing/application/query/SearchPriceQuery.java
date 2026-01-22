package com.jvjimenez.pricing.application.query;

import java.time.Instant;

public record SearchPriceQuery(
        Long brandId,
        Long productId,
        Instant searchDate
) {
}
