package com.jvjimenez.pricing.domain.service;

import com.jvjimenez.pricing.domain.model.Price;
import com.jvjimenez.pricing.domain.port.primary.PriceResult;
import com.jvjimenez.pricing.domain.port.primary.PriceService;
import com.jvjimenez.pricing.domain.port.secondary.PricePersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {
    private static final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);
    private final PricePersistenceService pricePersistentService;

    public PriceServiceImpl(PricePersistenceService priceRepository) {
        this.pricePersistentService = priceRepository;
    }

    public Optional<PriceResult> getPrice(Long brandId, Long productId, Instant searchDate) {
        log.debug("Getting price for brandId={}, productId={}, searchDate={}", brandId, productId, searchDate);
        return pricePersistentService
                .findByBrandAndProductAndDate(brandId, productId, searchDate)
                .map(price -> {
                    log.debug("Price found for brandId={}, productId={}, searchDate={}\", brandId, productId, searchDate", brandId, productId, searchDate);
                    return toPriceResult(price);
                });
    }

    private PriceResult toPriceResult(Price price) {
        return new PriceResult(
                price.brandId(),
                price.productId(),
                price.startDate(),
                price.endDate(),
                price.applicableRate(),
                price.price(),
                price.curr()
        );
    }
}
