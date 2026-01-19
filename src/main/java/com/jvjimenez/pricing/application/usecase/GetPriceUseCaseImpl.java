package com.jvjimenez.pricing.application.usecase;

import com.jvjimenez.pricing.application.query.SearchPriceQuery;
import com.jvjimenez.pricing.application.view.PriceSummary;
import com.jvjimenez.pricing.domain.model.Price;
import com.jvjimenez.pricing.domain.port.out.PricePersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetPriceUseCaseImpl implements GetPriceUseCase {
    private static final Logger log = LoggerFactory.getLogger(GetPriceUseCaseImpl.class);
    private final PricePersistencePort pricePersistentService;

    public GetPriceUseCaseImpl(PricePersistencePort priceRepository) {
        this.pricePersistentService = priceRepository;
    }

    public Optional<PriceSummary> getPrice(SearchPriceQuery query) {
        log.debug("Getting price for brandId={}, productId={}, searchDate={}", query.brand(), query.productId(), query.searchDate());
        return pricePersistentService
                .findByBrandAndProductAndDate(query.brand(), query.productId(), query.searchDate())
                .map(price -> {
                    log.debug("Price found for brandId={}, productId={}, searchDate={}\", brandId, productId, searchDate", query.brand(), query.productId(), query.searchDate());
                    return toPriceResult(price);
                });
    }

    private PriceSummary toPriceResult(Price price) {
        return new PriceSummary(
                price.brandId(),
                price.productId(),
                price.startDate(),
                price.endDate(),
                price.applicableRate(),
                price.price(),
                price.currency()
        );
    }
}
