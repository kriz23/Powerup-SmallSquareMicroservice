package com.pragma.powerup_smallsquaremicroservice.domain.spi;

public interface IRestaurantEmployeePersistencePort {
    void assignEmployeeToRestaurant(Long idRestaurant, Long idEmployee);
    boolean validateEmployeeExists(Long idEmployee);
    Long getRestaurantId(Long idEmployee);
}
