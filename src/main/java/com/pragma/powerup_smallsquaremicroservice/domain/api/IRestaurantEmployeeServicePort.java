package com.pragma.powerup_smallsquaremicroservice.domain.api;

public interface IRestaurantEmployeeServicePort {
    boolean assignEmployeeToRestaurant(String authHeader, Long idEmployee, Long idRestaurant);
    boolean validateEmployeeExists(Long idEmployee);
    boolean validateEmployeeExistsInternal(Long idEmployee);
    Long getRestaurantId(Long idEmployee);
}
