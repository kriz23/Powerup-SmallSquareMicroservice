package com.pragma.powerup_smallsquaremicroservice.domain.api;

public interface IRestaurantEmployeeServicePort {
    boolean assignEmployeeToRestaurant(String authHeader, Long idRestaurant, Long idEmployee);
    boolean validateEmployeeExists(Long idEmployee);
    boolean validateEmployeeExistsInternal(Long idEmployee);
    Long getRestaurantId(Long idEmployee);
}
