package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderDishResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IOrderDishResponseMapper {
    @Mapping(source = "order.id", target = "idOrder")
    @Mapping(source = "dish.id", target = "idDish")
    OrderDishResponseDto orderDishToOrderDishResponseDto(OrderDish orderDish);
    
    List<OrderDishResponseDto> orderDishListToOrderDishResponseDtoList(List<OrderDish> orderDishes);
    
}
