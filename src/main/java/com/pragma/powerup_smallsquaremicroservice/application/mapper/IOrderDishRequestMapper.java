package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderDishRequestDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IOrderDishRequestMapper {
    @Mapping(source = "idDish", target = "dish.id")
    OrderDish orderDishRequestDtoToOrderDish(OrderDishRequestDto orderDishRequestDto);
}
