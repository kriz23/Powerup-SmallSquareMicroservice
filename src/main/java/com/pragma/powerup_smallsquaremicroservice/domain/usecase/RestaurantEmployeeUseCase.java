package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.EmployeeNotFoundException;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;

public class RestaurantEmployeeUseCase implements IRestaurantEmployeeServicePort {
    
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final IUserMSClientPort userMSClientPort;
    private final IJwtServicePort jwtServicePort;
    
    public RestaurantEmployeeUseCase(IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort,
                                     IRestaurantServicePort restaurantServicePort, IUserMSClientPort userMSClientPort,
                                     IJwtServicePort jwtServicePort) {
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.restaurantServicePort = restaurantServicePort;
        this.userMSClientPort = userMSClientPort;
        this.jwtServicePort = jwtServicePort;
    }
    
    @Override
    public boolean assignEmployeeToRestaurant(String authHeader, Long idRestaurant, Long idEmployee) {
        if (restaurantServicePort.validateRestaurantOwnership(authHeader, idRestaurant)){
            restaurantEmployeePersistencePort.assignEmployeeToRestaurant(idRestaurant, idEmployee);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean validateEmployeeExists(Long idEmployee) {
        return restaurantEmployeePersistencePort.validateEmployeeExists(idEmployee);
    }
    
    @Override
    public boolean validateEmployeeExistsInternal(Long idEmployee) {
        if (validateEmployeeExists(idEmployee)){
            return true;
        } else {
            throw new EmployeeNotFoundException();
        }
    }
    
    @Override
    public Long getRestaurantId(Long idEmployee) {
        return restaurantEmployeePersistencePort.getRestaurantId(idEmployee);
    }
}
