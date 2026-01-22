package com.jvjimenez.pricing.infrastructure.adapter.in.rest.controller;

import com.jvjimenez.pricing.application.query.SearchPriceQuery;
import com.jvjimenez.pricing.application.usecase.GetPriceUseCase;
import com.jvjimenez.pricing.application.view.PriceSummary;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.ErrorResponseDto;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceRequestDto;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.mapper.PriceResponseDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1")
@Tag(name = "Prices", description = "Price management API")
public class PriceController {

    private final GetPriceUseCase priceService;
    private final PriceResponseDtoMapper priceResponseMapper;

    public PriceController(GetPriceUseCase priceService, PriceResponseDtoMapper priceResponseMapper) {
        this.priceService = priceService;
        this.priceResponseMapper = priceResponseMapper;
    }

    @Operation(
            summary = "Get applicable price",
            description = "Retrieves the applicable price for a product based on brand, product ID, and application date. " +
                    "If multiple prices exist for the given criteria, returns the one with highest priority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Search executed successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PriceResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping(
            path = "/prices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceResponseDto> getPrice(
            @ParameterObject @Valid @Parameter(description = "Price search criteria") PriceRequestDto requestDto) {
        SearchPriceQuery query = new SearchPriceQuery(
                requestDto.brandId(),
                requestDto.productId(),
                requestDto.searchDate()
        );
        PriceSummary price = priceService.getPrice(query);
        PriceResponseDto dto = priceResponseMapper.toDto(price);
        return ResponseEntity.ok(dto);
    }
}

