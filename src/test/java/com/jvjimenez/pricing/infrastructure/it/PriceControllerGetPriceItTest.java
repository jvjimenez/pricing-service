package com.jvjimenez.pricing.infrastructure.it;

import com.jvjimenez.pricing.infrastructure.api.rest.model.PriceResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PriceControllerGetPriceItTest extends BaseItTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final long PRODUCT_ID = 35455L;
    private static final long BRAND_ID = 1L;

    public Stream<Arguments> shouldReturn200GetPrice() {
        return Stream.of(
                Arguments.of("2020-06-14T10:00:00Z", new BigDecimal("35.50")),
                Arguments.of("2020-06-14T16:00:00Z", new BigDecimal("25.45")),
                Arguments.of("2020-06-14T21:00:00Z", new BigDecimal("35.50")),
                Arguments.of("2020-06-15T10:00:00Z", new BigDecimal("30.50")),
                Arguments.of("2020-06-16T21:00:00Z", new BigDecimal("38.95"))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldReturn200GetPrice")
    void shouldReturn200GetPrice(String date, BigDecimal expectedPrice) {
        String url = url("/api/price?searchDate=" + date +
                "&productId=" + PRODUCT_ID + "&brandId=" + BRAND_ID);
        ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(url, PriceResponseDto.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(expectedPrice);
    }

    @Test
    void shouldReturn404GetPricePriceNotFound() {
        String url = url("/api/price?searchDate=2020-01-01T00:00:00Z" +
                "&productId=99999&brandId=1");
        ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(url, PriceResponseDto.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturn400GetPriceInvalidDate() {
        String url = url("/api/price?searchDate=invalid-date" +
                "&productId=" + PRODUCT_ID + "&brandId=" + BRAND_ID);
        ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(url, PriceResponseDto.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturn400GetPriceNoMandatoryParams() {
        String url = url("/api/price?searchDate=2020-01-01T00:00:00Z");
        ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(url, PriceResponseDto.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}