package com.pragma.powerup_smallsquaremicroservice.domain.api;

public interface IRestaurantEmployeeServicePort {
    boolean assingEmployeeToRestaurant(String authHeader, Long idRestaurant, Long idEmployee);
}
