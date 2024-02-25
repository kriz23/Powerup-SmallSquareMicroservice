package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper;

import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeEntityMapper {
    
    @Mapping(source = "idRestaurant", target = "restaurantEntity.id")
    @Mapping(source = "idEmployee", target = "idEmployee")
    RestaurantEmployeeEntity restaurantEmployeeToRestaurantEmployeeEntity(Long idRestaurant, Long idEmployee);
}
