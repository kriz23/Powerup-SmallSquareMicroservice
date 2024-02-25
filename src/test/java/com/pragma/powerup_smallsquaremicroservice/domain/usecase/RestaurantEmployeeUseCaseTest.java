package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantEmployeeUseCaseTest {
    
    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    
    @InjectMocks
    private RestaurantEmployeeUseCase restaurantEmployeeUseCase;
    
    @Test
    void assingEmployeeToRestaurant_callsPersistencePort(){
        String authHeader = "validHeader";
        Long idRestaurant = 1L;
        Long idEmployee = 1L;
        when(restaurantServicePort.validateRestaurantOwnership(authHeader, idRestaurant)).thenReturn(true);
        
        assertTrue(restaurantEmployeeUseCase.assingEmployeeToRestaurant(authHeader, idRestaurant, idEmployee));
        verify(restaurantEmployeePersistencePort, times(1)).assingEmployeeToRestaurant(idRestaurant, idEmployee);
    }
    
    @Test
    void assingEmployeeToRestaurant_returnFalse(){
        String authHeader = "validHeader";
        Long idRestaurant = 1L;
        Long idEmployee = 1L;
        
        when(restaurantServicePort.validateRestaurantOwnership(authHeader, idRestaurant)).thenReturn(false);
        
        assertFalse(restaurantEmployeeUseCase.assingEmployeeToRestaurant(authHeader, idRestaurant, idEmployee));
    }
}
