package com.jvjimenez.pricing.infrastructure.adapter.out.persistence.repository;

import com.jvjimenez.pricing.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(PriceJpaRepository.class)
@TestPropertySource(properties = "spring.sql.init.mode=always")
class PriceJpaRepositoryTest {

    private final PriceJpaRepository repository;

    @Autowired
    PriceJpaRepositoryTest(PriceJpaRepository repository) {
        this.repository = repository;
    }

    @Test
    void shouldReturnHighestPriorityPrice() {
        Instant searchDate = Instant.parse("2020-06-14T16:00:00Z");

        Optional<Price> result = repository.findByBrandAndProductAndDate(1L, 35455L, searchDate);

        assertThat(result).isPresent();
        Price price = result.orElseThrow();
        assertThat(price.applicableRate()).isEqualTo(2L);
        assertThat(price.price()).isEqualByComparingTo(new BigDecimal("25.45"));
    }

    @Test
    void shouldReturnEmptyWhenNoPriceFound() {
        Instant searchDate = Instant.parse("2020-01-01T00:00:00Z");

        Optional<Price> result = repository.findByBrandAndProductAndDate(1L, 99999L, searchDate);

        assertThat(result).isEmpty();
    }
}
