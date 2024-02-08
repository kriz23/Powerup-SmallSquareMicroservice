package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IRestaurantHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {
    
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    
    @Override
    public void createRestaurant(RestaurantRequestDto restaurantRequestDto) {
        restaurantServicePort.createRestaurant(
                restaurantRequestMapper.restaurantRequestDtoToRestaurant(restaurantRequestDto));
    }
}
