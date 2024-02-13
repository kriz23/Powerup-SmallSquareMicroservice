package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.RestaurantRequestDto;

public interface IRestaurantHandler {
    void createRestaurant(RestaurantRequestDto restaurantRequestDto);
}
