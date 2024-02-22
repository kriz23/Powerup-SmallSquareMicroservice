package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.RestaurantRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface IRestaurantHandler {
    void createRestaurant(RestaurantRequestDto restaurantRequestDto, HttpServletRequest request);
}
