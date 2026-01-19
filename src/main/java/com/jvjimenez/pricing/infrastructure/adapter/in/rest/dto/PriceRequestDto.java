package com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@ParameterObject
public record PriceRequestDto(
        @NotNull Long brandId,
        @NotNull Long productId,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant searchDate
) {
}