package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Role;
import com.pragma.powerup_smallsquaremicroservice.domain.model.User;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    
    @Mock
    private IUserMSClientPort userMSClientPort;
    
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;
    
    @BeforeEach
    void setUp(){
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userMSClientPort);
    }
    
    @Test
    void createRestaurant_allValid_CallsPersistencePort(){
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573101234567", "www.logo.com", 1L);
        User owner = new User(1L, "John", "Doe", "123456789", "+573101234567",
                              LocalDate.of(2000, 1, 1), "john.doe@gmail.com",
                              "$2a$10$Pl5xVXG4JkXsJZ4krcIMAuDIDNCk8HKqeJYQ8gUjE64QfHjR0aSeu",
                              new Role(2L, "OWNER", "Owner"));
        when(userMSClientPort.getOwnerById(1L)).thenReturn(owner);
        restaurantUseCase.createRestaurant(restaurant);
        
        verify(restaurantPersistencePort, times(1)).createRestaurant(restaurant);
    }
    
    @Test
    void validateName_validName_returnsTrue(){
        String name = "Restaurant";
        
        assertTrue(restaurantUseCase.validateName(name));
    }
    
    @Test
    void validateName_invalidName_throwsException(){
        String name = "";
        
        assertThrows(RestaurantNameInvalidException.class, () -> restaurantUseCase.validateName(name));
    }
    
    @Test
    void validateNit_validNit_returnsTrue(){
        String nit = "123456789";
        
        assertTrue(restaurantUseCase.validateNIT(nit));
    }
    
    @Test
    void validateNit_invalidNit_throwsException(){
        String nit = "";
        
        assertThrows(RestaurantNitInvalidException.class, () -> restaurantUseCase.validateNIT(nit));
    }
    
    @Test
    void validateAddress_validAddress_returnsTrue(){
        String address = "Calle 123";
        
        assertTrue(restaurantUseCase.validateAddress(address));
    }
    
    @Test
    void validateAddress_invalidAddress_throwsException(){
        String address = "";
        
        assertThrows(RestaurantAddressInvalidException.class, () -> restaurantUseCase.validateAddress(address));
    }
    
    @Test
    void validatePhone_validPhone_returnsTrue(){
        String phone = "+573123456789";
        
        assertTrue(restaurantUseCase.validatePhone(phone));
    }
    
    @Test
    void validatePhone_invalidPhone_throwsException(){
        String phone = "";
        
        assertThrows(RestaurantPhoneInvalidException.class, () -> restaurantUseCase.validatePhone(phone));
    }
    
    @Test
    void validateUrlLogo_validUrlLogo_returnsTrue(){
        String urlLogo = "www.logo.com";
        
        assertTrue(restaurantUseCase.validateUrlLogo(urlLogo));
    }
    
    @Test
    void validateUrlLogo_invalidUrlLogo_throwsException(){
        String urlLogo = "";
        
        assertThrows(RestaurantUrlLogoInvalidException.class, () -> restaurantUseCase.validateUrlLogo(urlLogo));
    }
    
    @Test
    void validateIdOwner_validIdOwner_returnsTrue(){
        Long idOwner = 1L;
        
        assertTrue(restaurantUseCase.validateIdOwner(idOwner));
    }
    
    @Test
    void validateIdOwner_invalidIdOwner_throwsException(){
        Long idOwner = 0L;
        
        assertThrows(RestaurantIdOwnerInvalidException.class, () -> restaurantUseCase.validateIdOwner(idOwner));
    }
    
    
    @Test
    void validateOwnerRole_validIdOwner_returnsTrue(){
        User owner = new User(1L, "John", "Doe", "123456789", "+573101234567",
                              LocalDate.of(2000, 1, 1), "john.doe@gmail.com",
                              "$2a$10$Pl5xVXG4JkXsJZ4krcIMAuDIDNCk8HKqeJYQ8gUjE64QfHjR0aSeu",
                              new Role(2L, "OWNER", "Owner"));
        when(userMSClientPort.getOwnerById(1L)).thenReturn(owner);
        
        assertTrue(restaurantUseCase.validateOwnerRole(1L));
    }
    
    @Test
    void validateOwnerRole_invalidIdOwner_throwsException(){
        User owner = new User(1L, "John", "Doe", "123456789", "+573101234567",
                              LocalDate.of(2000, 1, 1), "john.doe@gmail.com",
                              "$2a$10$Pl5xVXG4JkXsJZ4krcIMAuDIDNCk8HKqeJYQ8gUjE64QfHjR0aSeu",
                              new Role(1L, "OWNER", "Owner"));
        when(userMSClientPort.getOwnerById(1L)).thenReturn(owner);
        
        assertThrows(RoleNotAllowedException.class, () -> restaurantUseCase.validateOwnerRole(1L));
    }
    
    @Test
    void validateOwnerRole_invalidIdOwner_throwsException2(){
        when(userMSClientPort.getOwnerById(1L)).thenThrow(FeignException.FeignClientException.class);;
        
        assertThrows(OwnerNotFoundException.class, () -> restaurantUseCase.validateOwnerRole(1L));
    }
}
