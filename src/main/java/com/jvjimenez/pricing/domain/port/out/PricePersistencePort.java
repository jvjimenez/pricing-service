package com.jvjimenez.pricing.domain.port.out;

import com.jvjimenez.pricing.domain.model.Price;

import java.time.Instant;
import java.util.Optional;

public interface PricePersistencePort {
    Optional<Price> findByBrandAndProductAndDate(Long brandId, Long productId, Instant applicationDate);
}
