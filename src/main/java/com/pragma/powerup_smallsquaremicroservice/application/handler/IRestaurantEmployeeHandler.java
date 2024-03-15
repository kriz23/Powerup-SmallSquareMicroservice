package com.pragma.powerup_smallsquaremicroservice.application.handler;

import javax.servlet.http.HttpServletRequest;

public interface IRestaurantEmployeeHandler {
    boolean assignEmployeeToRestaurant(Long idEmployee, Long idRestaurant, HttpServletRequest request);
}
