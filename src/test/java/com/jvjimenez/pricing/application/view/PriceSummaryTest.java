package com.jvjimenez.pricing.application.view;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PriceSummaryTest {

    @Test
    void shouldCreatePriceSummaryRecord() {
        Instant start = Instant.parse("2020-06-14T15:00:00Z");
        Instant end = Instant.parse("2020-06-14T18:30:00Z");

        PriceSummary summary = new PriceSummary(
                1L, 35455L,
                start, end, 1L,
                new BigDecimal("25.45"), "EUR"
        );
        assertThat(summary.brandId()).isEqualTo(1L);
        assertThat(summary.productId()).isEqualTo(35455L);
        assertThat(summary.price()).isEqualByComparingTo(new BigDecimal("25.45"));
        assertThat(summary.currency()).isEqualTo("EUR");
        assertThat(summary.startDate()).isEqualTo(start);
        assertThat(summary.endDate()).isEqualTo(end);
    }
}