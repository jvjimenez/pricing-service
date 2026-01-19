package com.jvjimenez.pricing.infrastructure.adapter.in.rest.mapper;

import com.jvjimenez.pricing.application.view.PriceSummary;
import com.jvjimenez.pricing.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceResponseDtoMapper {

    PriceResponseDto toDto(PriceSummary result);
}
