package com.jvjimenez.pricing.infrastructure.adapter.in.rest.mapper;

import com.jvjimenez.pricing.application.usecase.GetPriceUseCase;
import com.jvjimenez.pricing.application.view.PriceSummary;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.controller.PriceController;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceRequestDto;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class PriceControllerTest {

    @Mock
    private GetPriceUseCase service;
    @Mock
    private PriceResponseDtoMapper mapper;

    @InjectMocks
    private PriceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new PriceController(service, mapper);
    }


    @Test
    void shouldReturnPriceResponseDto() {
        Instant searchDate = Instant.parse("2020-06-14T16:00:00Z");
        var summary = new PriceSummary(
                1L, 35455L,
                Instant.parse("2020-06-14T15:00:00Z"),
                Instant.parse("2020-06-14T18:30:00Z"),
                2L,
                new java.math.BigDecimal("25.45"),
                "EUR"
        );

        when(service.getPrice(any())).thenReturn(Optional.of(summary));
        when(mapper.toDto(summary)).thenReturn(new PriceResponseDto(
                1L, 35455L, 2L, new java.math.BigDecimal("25.45"), "EUR",
                Instant.parse("2020-06-14T15:00:00Z"),
                Instant.parse("2020-06-14T18:30:00Z")
        ));

        var response = controller.getPrice(new PriceRequestDto(1L, 35455L, searchDate));

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().price()).isEqualByComparingTo(new BigDecimal("25.45"));

        verify(service, times(1)).getPrice(any());

    }
}
