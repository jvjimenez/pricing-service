package com.jvjimenez.pricing.domain.port.secondary;

import com.jvjimenez.pricing.domain.model.Price;

import java.time.Instant;
import java.util.Optional;

public interface PricePersistenceService {
    Optional<Price> findByBrandAndProductAndDate(Long brandId, Long productId, Instant applicationDate);
}
