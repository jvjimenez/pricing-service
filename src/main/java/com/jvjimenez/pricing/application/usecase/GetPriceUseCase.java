package com.jvjimenez.pricing.application.usecase;

import com.jvjimenez.pricing.application.query.SearchPriceQuery;
import com.jvjimenez.pricing.application.view.PriceSummary;

public interface GetPriceUseCase {
    PriceSummary getPrice(SearchPriceQuery query);
}
