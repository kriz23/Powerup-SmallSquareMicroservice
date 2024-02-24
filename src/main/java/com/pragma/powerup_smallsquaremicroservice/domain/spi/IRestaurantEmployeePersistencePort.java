package com.pragma.powerup_smallsquaremicroservice.domain.spi;

public interface IRestaurantEmployeePersistencePort {
    void assingEmployeeToRestaurant(Long idRestaurant, Long idEmployee);
}
