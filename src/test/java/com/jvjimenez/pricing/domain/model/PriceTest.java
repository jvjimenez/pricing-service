package com.jvjimenez.pricing.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void isApplicableOnShouldIncludeBounds() {
        Instant start = Instant.parse("2020-06-14T15:00:00Z");
        Instant end = Instant.parse("2020-06-14T18:30:00Z");
        Price price = new Price(1L, 35455L, start, end, 1L, 0, new BigDecimal("25.45"), "EUR");

        assertThat(price.isApplicableOn(start)).isTrue();
        assertThat(price.isApplicableOn(end)).isTrue();
    }

    @Test
    void isApplicableOnShouldExcludeOutsideRange() {
        Instant start = Instant.parse("2020-06-14T15:00:00Z");
        Instant end = Instant.parse("2020-06-14T18:30:00Z");
        Price price = new Price(1L, 35455L, start, end, 1L, 0, new BigDecimal("25.45"), "EUR");

        assertThat(price.isApplicableOn(Instant.parse("2020-06-14T14:59:59Z"))).isFalse();
        assertThat(price.isApplicableOn(Instant.parse("2020-06-14T18:30:01Z"))).isFalse();
    }
}
