package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishRequestDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IDishRequestMapper {
    @Mapping(source = "idCategory", target = "category.id")
    @Mapping(source = "idRestaurant", target = "restaurant.id")
    Dish dishRequestDtoToDish(DishRequestDto dishRequestDto);
}
