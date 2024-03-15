package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.RestaurantEmployee;

import java.util.List;

public interface IRestaurantEmployeePersistencePort {
    void assignEmployeeToRestaurant(Long idEmployee, Long idRestaurant);
    boolean validateEmployeeExists(Long idEmployee);
    Long getRestaurantId(Long idEmployee);
    List<RestaurantEmployee> getEmployeesByIdRestaurant(Long idRestaurant);
}
