package com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response structure")
public record ErrorResponseDto(

        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "Error message", example = "Invalid request parameters")
        String message
) {
}