package com.jvjimenez.pricing.domain.port.primary;

import java.time.Instant;
import java.util.Optional;

public interface PriceService {
    Optional<PriceResult> getPrice(Long brandId, Long productId, Instant searchDate);
}
