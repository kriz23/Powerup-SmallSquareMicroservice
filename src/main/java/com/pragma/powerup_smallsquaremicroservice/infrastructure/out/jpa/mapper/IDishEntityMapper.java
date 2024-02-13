package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IDishEntityMapper {
    @Mapping(source = "category.id", target = "categoryEntity.id")
    @Mapping(source = "restaurant.id", target = "restaurantEntity.id")
    DishEntity dishToDishEntity(Dish dish);
    
    @Mapping(source = "categoryEntity.id", target = "category.id")
    @Mapping(source = "restaurantEntity.id", target = "restaurant.id")
    Dish dishEntityToDish(DishEntity dishEntity);
}
