package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.handler.IRestaurantEmployeeHandler;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantEmployeeServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantEmployeeHandler implements IRestaurantEmployeeHandler {
    private final IRestaurantEmployeeServicePort restaurantEmployeeServicePort;
    
    @Override
    public boolean assignEmployeeToRestaurant(Long idRestaurant, Long idEmployee, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return restaurantEmployeeServicePort.assingEmployeeToRestaurant(authHeader, idRestaurant, idEmployee);
    }
}
