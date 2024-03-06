package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE, uses = {IOrderDishResponseMapper.class})
public interface IOrderResponseMapper {
    IOrderDishResponseMapper INSTANCE = Mappers.getMapper(IOrderDishResponseMapper.class);
    
    @Mapping(source = "restaurant.id", target = "idRestaurant")
    @Mapping(source = "orderDishes", target = "orderDishes")
    OrderResponseDto orderToOrderResponseDto(Order order);
    
    List<OrderResponseDto> orderListToOrderResponseDtoList(List<Order> orders);
}
