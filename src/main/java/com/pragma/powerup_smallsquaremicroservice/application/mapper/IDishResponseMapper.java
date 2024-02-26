package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishSimpleResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE, uses = {ICategoryResponseMapper.class, IRestaurantResponseMapper.class})
public interface IDishResponseMapper {
    ICategoryResponseMapper INSTANCE = Mappers.getMapper(ICategoryResponseMapper.class);
    IRestaurantResponseMapper INSTANCE2 = Mappers.getMapper(IRestaurantResponseMapper.class);
    
    @Mapping(source = "category", target = "category")
    @Mapping(source = "restaurant", target = "restaurant")
    DishResponseDto dishToDishResponseDto(Dish dish);
    
    @Mapping(source = "category.id", target = "idCategory")
    @Mapping(source = "restaurant.id", target = "idRestaurant")
    DishSimpleResponseDto dishToDishSimpleResponseDto(Dish dish);
    
}
