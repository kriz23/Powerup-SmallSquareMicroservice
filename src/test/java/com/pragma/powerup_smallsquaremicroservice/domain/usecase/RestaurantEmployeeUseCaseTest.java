package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.EmployeeNotFoundException;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
        
        assertTrue(restaurantEmployeeUseCase.assignEmployeeToRestaurant(authHeader, idEmployee, idRestaurant));
        verify(restaurantEmployeePersistencePort, times(1)).assignEmployeeToRestaurant(idEmployee, idRestaurant);
    }
    
    @Test
    void assingEmployeeToRestaurant_returnFalse(){
        String authHeader = "validHeader";
        Long idRestaurant = 1L;
        Long idEmployee = 1L;
        
        when(restaurantServicePort.validateRestaurantOwnership(authHeader, idRestaurant)).thenReturn(false);
        
        assertFalse(restaurantEmployeeUseCase.assignEmployeeToRestaurant(authHeader, idEmployee, idRestaurant));
    }
    
    @Test
    void validateEmployeeExists_callsPersistencePort(){
        Long idEmployee = 1L;
        when(restaurantEmployeePersistencePort.validateEmployeeExists(idEmployee)).thenReturn(true);
        
        assertTrue(restaurantEmployeeUseCase.validateEmployeeExists(idEmployee));
        verify(restaurantEmployeePersistencePort, times(1)).validateEmployeeExists(idEmployee);
    }
    
    @Test
    void validateEmployeeExistsInternal_returnTrue(){
        Long idEmployee = 1L;
        when(restaurantEmployeePersistencePort.validateEmployeeExists(idEmployee)).thenReturn(true);
        
        assertTrue(restaurantEmployeeUseCase.validateEmployeeExistsInternal(idEmployee));
    }
    
    @Test
    void validateEmployeeExistsInternal_throwsException(){
        Long idEmployee = 1L;
        when(restaurantEmployeePersistencePort.validateEmployeeExists(idEmployee)).thenReturn(false);
        
        assertThrows(EmployeeNotFoundException.class, () -> restaurantEmployeeUseCase.validateEmployeeExistsInternal(idEmployee));
    }
    
    @Test
    void getRestaurantId_callsPersistencePort(){
        Long idEmployee = 1L;
        when(restaurantEmployeePersistencePort.getRestaurantId(idEmployee)).thenReturn(6L);
        
        assertEquals(6L, restaurantEmployeeUseCase.getRestaurantId(idEmployee));
        verify(restaurantEmployeePersistencePort, times(1)).getRestaurantId(idEmployee);
    }
}
