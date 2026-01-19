package com.jvjimenez.pricing.application.usecase;

import com.jvjimenez.pricing.application.query.SearchPriceQuery;
import com.jvjimenez.pricing.application.view.PriceSummary;

import java.util.Optional;

public interface GetPriceUseCase {
    Optional<PriceSummary> getPrice(SearchPriceQuery query);
}
