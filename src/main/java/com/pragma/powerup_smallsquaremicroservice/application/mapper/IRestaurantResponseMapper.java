package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.RestaurantSimpleResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    RestaurantResponseDto restaurantToRestaurantResponseDto(Restaurant restaurant);
    
    List<RestaurantResponseDto> restaurantToResponseDtoList(List<Restaurant> restaurantList);
    
    RestaurantSimpleResponseDto restaurantToRestaurantSimpleResponseDto(Restaurant restaurant);
}
