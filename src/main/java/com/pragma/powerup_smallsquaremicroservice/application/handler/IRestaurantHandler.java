package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishSimpleResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.RestaurantSimpleResponseDto;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface IRestaurantHandler {
    void createRestaurant(RestaurantRequestDto restaurantRequestDto, HttpServletRequest request);
    boolean validateRestaurantOwnership(Long idRestaurant, HttpServletRequest request);
    Page<RestaurantSimpleResponseDto> getAllRestaurantsPageable(int page, int size);
    Page<DishSimpleResponseDto> getAllDishesFromRestaurantPageable(Long idRestaurant, Long idCategory, int page, int size);
}
