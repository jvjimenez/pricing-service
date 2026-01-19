package com.jvjimenez.pricing.application.query;

import java.time.Instant;

public record SearchPriceQuery(
        Long brand,
        Long productId,
        Instant searchDate
) {
}