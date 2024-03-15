package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper;

import com.pragma.powerup_smallsquaremicroservice.domain.model.RestaurantEmployee;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeEntityMapper {
    
    @Mapping(source = "idEmployee", target = "idEmployee")
    @Mapping(source = "idRestaurant", target = "restaurantEntity.id")
    RestaurantEmployeeEntity restaurantEmployeeToRestaurantEmployeeEntity(Long idEmployee, Long idRestaurant);
    
    List<RestaurantEmployee> restaurantEmployeeEntityListToRestaurantEmployeeList(List<RestaurantEmployeeEntity> restaurantEmployeeEntityList);
}
