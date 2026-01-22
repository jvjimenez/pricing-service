package com.jvjimenez.pricing.infrastructure.adapter.in.rest.controller;

import com.jvjimenez.pricing.application.exception.PriceNotFoundException;
import com.jvjimenez.pricing.application.query.SearchPriceQuery;
import com.jvjimenez.pricing.application.usecase.GetPriceUseCase;
import com.jvjimenez.pricing.application.view.PriceSummary;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.exception.GlobalExceptionHandler;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.mapper.PriceResponseDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import(GlobalExceptionHandler.class)
class PriceControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetPriceUseCase getPriceUseCase;

    @MockitoBean
    private PriceResponseDtoMapper mapper;

    @Test
    void shouldReturn200WhenRequestIsValid() throws Exception {
        Instant searchDate = Instant.parse("2020-06-14T16:00:00Z");
        PriceSummary summary = new PriceSummary(
                1L, 35455L,
                Instant.parse("2020-06-14T15:00:00Z"),
                Instant.parse("2020-06-14T18:30:00Z"),
                2L,
                new BigDecimal("25.45"),
                "EUR"
        );
        PriceResponseDto response = new PriceResponseDto(
                1L, 35455L, 2L, new BigDecimal("25.45"), "EUR",
                Instant.parse("2020-06-14T15:00:00Z"),
                Instant.parse("2020-06-14T18:30:00Z")
        );

        when(getPriceUseCase.getPrice(any(SearchPriceQuery.class))).thenReturn(summary);
        when(mapper.toDto(summary)).thenReturn(response);

        mockMvc.perform(get("/api/v1/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("searchDate", searchDate.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.applicableRate").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    void shouldReturn404WhenPriceNotFound() throws Exception {
        when(getPriceUseCase.getPrice(any(SearchPriceQuery.class)))
                .thenThrow(new PriceNotFoundException("brandId=1"));

        mockMvc.perform(get("/api/v1/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("searchDate", "2020-06-14T16:00:00Z")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturn400WhenDateIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("searchDate", "invalid-date")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("searchDate")));
    }

    @Test
    void shouldReturn400WhenMissingParams() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("searchDate", "2020-06-14T16:00:00Z")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("Invalid request parameters")));
    }
}
