package com.jvjimenez.pricing.infrastructure.adapter.in.rest.mapper;

import com.jvjimenez.pricing.application.view.PriceSummary;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


class PriceResponseDtoMapperTest {
    private final PriceResponseDtoMapper mapper = Mappers.getMapper(PriceResponseDtoMapper.class);

    @Test
    void shouldMapPriceToPriceResponse() {
        PriceSummary price = new PriceSummary(
                1L,
                35455L,
                Instant.parse("2020-06-14T00:00:00Z"),
                Instant.parse("2020-06-14T23:59:59Z"),
                1L,
                new BigDecimal("35.50"),
                "EUR"
        );

        PriceResponseDto response = mapper.toDto(price);
        assertThat(response).isNotNull();
        assertThat(response.brandId()).isEqualTo(1L);
        assertThat(response.productId()).isEqualTo(35455L);
        assertThat(response.applicableRate()).isEqualTo(1L);
        assertThat(response.startDate()).isEqualTo(Instant.parse("2020-06-14T00:00:00Z"));
        assertThat(response.endDate()).isEqualTo(Instant.parse("2020-06-14T23:59:59Z"));
        assertThat(response.price()).isEqualByComparingTo(new BigDecimal("35.50"));
        assertThat(response.currency()).isEqualTo("EUR");
    }
}
