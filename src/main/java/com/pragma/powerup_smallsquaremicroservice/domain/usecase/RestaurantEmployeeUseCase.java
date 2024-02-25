package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;

public class RestaurantEmployeeUseCase implements IRestaurantEmployeeServicePort {
    
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IRestaurantServicePort restaurantServicePort;
    
    public RestaurantEmployeeUseCase(IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort,
                                     IRestaurantServicePort restaurantServicePort) {
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.restaurantServicePort = restaurantServicePort;
    }
    
    @Override
    public boolean assingEmployeeToRestaurant(String authHeader, Long idRestaurant, Long idEmployee) {
        if (restaurantServicePort.validateRestaurantOwnership(authHeader, idRestaurant)){
            restaurantEmployeePersistencePort.assingEmployeeToRestaurant(idRestaurant, idEmployee);
            return true;
        }
        return false;
    }
}
