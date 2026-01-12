package com.jvjimenez.pricing.infrastructure.mapper;

import com.jvjimenez.pricing.domain.port.primary.PriceResult;
import com.jvjimenez.pricing.infrastructure.api.rest.mapper.PriceResponseMapper;
import com.jvjimenez.pricing.infrastructure.api.rest.model.PriceResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


class PriceResponseDtoMapperTest {
    private final PriceResponseMapper mapper = Mappers.getMapper(PriceResponseMapper.class);

    @Test
    void shouldMapPriceToPriceResponse() {
        PriceResult price = new PriceResult(
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
        assertThat(response.getBrandId()).isEqualTo(1L);
        assertThat(response.getProductId()).isEqualTo(35455L);
        assertThat(response.getStartDate()).isEqualTo(Instant.parse("2020-06-14T00:00:00Z"));
        assertThat(response.getEndDate()).isEqualTo(Instant.parse("2020-06-14T23:59:59Z"));
        assertThat(response.getPrice()).isEqualByComparingTo(new BigDecimal("35.50"));
        assertThat(response.getCurr()).isEqualTo("EUR");
    }
}
