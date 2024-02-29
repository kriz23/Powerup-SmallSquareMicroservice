package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishSimpleResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.RestaurantSimpleResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IRestaurantHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IDishResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.RestaurantPhoneInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {
    
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IDishResponseMapper dishResponseMapper;
    
    @Override
    public void createRestaurant(RestaurantRequestDto restaurantRequestDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (restaurantRequestDto.getPhone().length() <= 10 && restaurantRequestDto.getPhone().contains("+57")) {
            throw new RestaurantPhoneInvalidException();
        }
        if (restaurantRequestDto.getPhone().length() == 10 && !restaurantRequestDto.getPhone().contains("+57")) {
            restaurantRequestDto.setPhone("+57" + restaurantRequestDto.getPhone());
        }
        restaurantServicePort.createRestaurant(authHeader,
                restaurantRequestMapper.restaurantRequestDtoToRestaurant(restaurantRequestDto));
    }
    
    @Override
    public boolean validateRestaurantOwnership(Long idRestaurant, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return restaurantServicePort.validateRestaurantOwnership(authHeader, idRestaurant);
    }
    
    @Override
    public Page<RestaurantSimpleResponseDto> getAllRestaurantsPageable(int page, int size) {
        return restaurantServicePort.getAllRestaurantsPageable(page, size)
                                    .map(restaurantResponseMapper::restaurantToRestaurantSimpleResponseDto);
    }
    
    @Override
    public Page<DishSimpleResponseDto> getAllDishesFromRestaurantByCategoryPageable(Long idRestaurant, Long idCategory, int page,
                                                                                    int size) {
        return restaurantServicePort.getAllDishesFromRestaurantByCategoryPageable(idRestaurant, idCategory, page, size)
                                    .map(dishResponseMapper::dishToDishSimpleResponseDto);
    }
}
