package com.jvjimenez.pricing.infrastructure.api.rest.mapper;

import com.jvjimenez.pricing.domain.port.primary.PriceResult;
import com.jvjimenez.pricing.infrastructure.api.rest.model.PriceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

    PriceResponseDto toDto(PriceResult result);
}
