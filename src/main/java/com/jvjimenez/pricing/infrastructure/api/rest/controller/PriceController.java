package com.jvjimenez.pricing.infrastructure.api.rest.controller;

import com.jvjimenez.pricing.domain.port.primary.PriceService;
import com.jvjimenez.pricing.infrastructure.api.rest.mapper.PriceResponseMapper;
import com.jvjimenez.pricing.infrastructure.api.rest.model.PriceRequestDto;
import com.jvjimenez.pricing.infrastructure.api.rest.model.PriceResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final PriceService priceService;
    private final PriceResponseMapper priceResponseMapper;

    public PriceController(PriceService priceService, PriceResponseMapper priceResponseMapper) {
        this.priceService = priceService;
        this.priceResponseMapper = priceResponseMapper;
    }

    @GetMapping(
            path = "/price",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceResponseDto> getPrice(
            @Valid PriceRequestDto priceRequestDto) {
        return priceService.getPrice(priceRequestDto.getBrandId(), priceRequestDto.getProductId(), priceRequestDto.getSearchDate())
                .map(priceResponseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

