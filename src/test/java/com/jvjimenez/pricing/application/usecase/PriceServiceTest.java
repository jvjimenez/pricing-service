package com.jvjimenez.pricing.application.usecase;

import com.jvjimenez.pricing.application.query.SearchPriceQuery;
import com.jvjimenez.pricing.application.view.PriceSummary;
import com.jvjimenez.pricing.domain.model.Price;
import com.jvjimenez.pricing.domain.port.out.PricePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private PricePersistencePort repository;
    private GetPriceUseCaseImpl service;

    @BeforeEach
    void setup() {
        repository = mock(PricePersistencePort.class);
        service = new GetPriceUseCaseImpl(repository);
    }

    @Test
    void shouldReturnEmptyWhenNoPricesFound() {
        when(repository.findByBrandAndProductAndDate(anyLong(), anyLong(), any()))
                .thenReturn(Optional.empty());

        Optional<PriceSummary> result = service.getPrice(new SearchPriceQuery(1L, 1L, Instant.now()));

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnPriceWithinDateRange() {
        Instant now = Instant.parse("2020-06-14T16:00:00Z");
        Price p1 = new Price(1L, 35455L, Instant.parse("2020-06-14T15:00:00Z"),
                Instant.parse("2020-06-14T18:30:00Z"), 1L, 2, new BigDecimal("25.45"), "EUR");

        when(repository.findByBrandAndProductAndDate(1L, 35455L, now)).thenReturn(Optional.of(p1));

        Optional<PriceSummary> result = service.getPrice(new SearchPriceQuery(1L, 35455L, now));

        assertThat(result).isPresent();
        assertThat(result.get().price()).isEqualByComparingTo(new BigDecimal("25.45"));
    }
}
