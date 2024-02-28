package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE, uses = {IOrderDishRequestMapper.class})
public interface IOrderRequestMapper {
    IOrderDishRequestMapper INSTANCE = Mappers.getMapper(IOrderDishRequestMapper.class);
    @Mapping(source = "idRestaurant", target = "restaurant.id")
    Order orderRequestDtoToOrder(OrderRequestDto orderRequestDto);
}
