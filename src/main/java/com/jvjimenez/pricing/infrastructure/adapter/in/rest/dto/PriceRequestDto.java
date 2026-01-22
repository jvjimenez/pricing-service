package com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@ParameterObject
public record PriceRequestDto(
        @NotNull(message = "brandId is required") Long brandId,
        @NotNull(message = "productId is required") Long productId,
        @NotNull(message = "searchDate is required") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant searchDate
) {
}