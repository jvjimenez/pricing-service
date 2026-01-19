package com.jvjimenez.pricing.infrastructure.adapter.in.rest.controller;

import com.jvjimenez.pricing.application.query.SearchPriceQuery;
import com.jvjimenez.pricing.application.usecase.GetPriceUseCase;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceRequestDto;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.mapper.PriceResponseDtoMapper;
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
@RequestMapping("/api")
public class PriceController {

    private final GetPriceUseCase priceService;
    private final PriceResponseDtoMapper priceResponseMapper;

    public PriceController(GetPriceUseCase priceService, PriceResponseDtoMapper priceResponseMapper) {
        this.priceService = priceService;
        this.priceResponseMapper = priceResponseMapper;
    }

    @GetMapping(
            path = "/price",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceResponseDto> getPrice(
            @ParameterObject @Valid PriceRequestDto requestDto) {
        SearchPriceQuery query = new SearchPriceQuery(
                requestDto.brandId(),
                requestDto.productId(),
                requestDto.searchDate()
        );
        return priceService.getPrice(query)
                .map(priceResponseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

