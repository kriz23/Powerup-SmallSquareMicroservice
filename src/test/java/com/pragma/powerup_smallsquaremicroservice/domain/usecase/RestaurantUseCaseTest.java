package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Role;
import com.pragma.powerup_smallsquaremicroservice.domain.model.User;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    
    @Mock
    private IUserMSClientPort userMSClientPort;
    
    @Mock
    private IJwtServicePort jwtServicePort;
    
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;
    
    @Test
    void createRestaurant_allValid_CallsPersistencePort(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(1L, "John", "Doe","123456789","+573101234567",
                                    LocalDate.of(2000, 1, 1),"admin@mail.com", "password",
                                     new Role(1L, "ROLE_ADMIN", "Admin")));
        when(userMSClientPort.getOwnerById(authHeader, 2L))
                .thenReturn(new User(2L, "John", "Doe","987654321","+573101234455",
                                    LocalDate.of(2000, 1, 1),"owner@mail.com", "password",
                                     new Role(2L, "ROLE_PROPIETARIO", "Propietario")));
        
        restaurantUseCase.createRestaurant(authHeader, restaurant);
        
        verify(restaurantPersistencePort, times(1)).createRestaurant(restaurant);
    }
    
    @Test
    void getAllRestaurantsByPage_allValid_callsPersistencePort(){
        int page = 0;
        int size = 10;
        
        restaurantUseCase.getAllRestaurantsByPage(page, size);
        
        verify(restaurantPersistencePort, times(1)).getAllRestaurantsByPage(page, size);
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
    void validateOwnerRoleFromRequest_validIdOwner_returnsTrue(){
        String authHeader = "validHeader";
        User owner = new User(1L, "John", "Doe", "123456789", "+573101234567",
                              LocalDate.of(2000, 1, 1), "john.doe@gmail.com",
                              "$2a$10$Pl5xVXG4JkXsJZ4krcIMAuDIDNCk8HKqeJYQ8gUjE64QfHjR0aSeu",
                              new Role(2L, "ROLE_PROPIETARIO", "Propietario"));
        when(userMSClientPort.getOwnerById(authHeader, 1L)).thenReturn(owner);
        
        assertTrue(restaurantUseCase.validateOwnerRoleFromRequest(authHeader, 1L));
    }
    
    @Test
    void validateOwnerRoleFromRequest_invalidIdOwner_throwsException(){
        String authHeader = "validHeader";
        User owner = new User(1L, "John", "Doe", "123456789", "+573101234567",
                              LocalDate.of(2000, 1, 1), "john.doe@gmail.com",
                              "$2a$10$Pl5xVXG4JkXsJZ4krcIMAuDIDNCk8HKqeJYQ8gUjE64QfHjR0aSeu",
                              new Role(1L, "ROLE_ADMIN", "Admin"));
        when(userMSClientPort.getOwnerById(authHeader, 1L)).thenReturn(owner);
        
        assertThrows(RoleNotAllowedException.class, () -> restaurantUseCase.validateOwnerRoleFromRequest(authHeader,
                                                                                                         1L));
    }
    
    @Test
    void validateOwnerRoleFromRequest_invalidIdOwner_throwsException2(){
        String authHeader = "validHeader";
        when(userMSClientPort.getOwnerById(authHeader, 1L)).thenThrow(FeignException.FeignClientException.class);;
        
        assertThrows(OwnerNotFoundException.class, () -> restaurantUseCase.validateOwnerRoleFromRequest(authHeader,
                                                                                                        1L));
    }
    
    @Test
    void validateRequestAdminRole_invalidRole_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(1L, "John", "Doe","123456789","+573101234567",
                                    LocalDate.of(2000, 1, 1),"admin@mail.com", "password",
                                     new Role(2L, "ROLE_PROPIETARIO", "Propietario")));
        assertThrows(UnauthorizedRoleException.class, () -> restaurantUseCase.validateRequestAdminRole(authHeader));
    }
    
    @Test
    void validateRestaurantOwnership_validOwnership_returnsTrue(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        String requestUserMail = "validRequestUserMail";
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(2L, "John", "Doe","987654321","+573101234455",
                                     LocalDate.of(2000, 1, 1),"owner@mail.com", "password",
                                     new Role(2L, "ROLE_PROPIETARIO", "Propietario")));
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        
        assertTrue(restaurantUseCase.validateRestaurantOwnership(authHeader, 1L));
    }
    
    @Test
    void validateRestaurantOwnership_invalidOwnership_returnsFalse(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 3L);
        String requestUserMail = "validRequestUserMail";
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(2L, "John", "Doe","987654321","+573101234455",
                                     LocalDate.of(2000, 1, 1),"owner@mail.com", "password",
                                     new Role(2L, "ROLE_PROPIETARIO", "Propietario")));
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        
        assertFalse(restaurantUseCase.validateRestaurantOwnership(authHeader, 1L));
    }
    
    @Test
    void validateRestaurantOwnership_restaurantDoesNotExists_returnsFalse(){
        String authHeader = "validHeader";
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(false);
        
        assertFalse(restaurantUseCase.validateRestaurantOwnership(authHeader, 1L));
    }
    
    @Test
    void validateRestaurantOwnershipInternal_validOwnership_returnsTrue(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        String requestUserMail = "validRequestUserMail";
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(2L, "John", "Doe","987654321","+573101234455",
                                     LocalDate.of(2000, 1, 1),"owner@mail.com", "password",
                                     new Role(2L, "ROLE_PROPIETARIO", "Propietario")));
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        
        assertTrue(restaurantUseCase.validateRestaurantOwnershipInternal(authHeader, 1L));
    }
    
    @Test
    void validateRestaurantOwnershipInternal_invalidOwnership_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 3L);
        String requestUserMail = "validRequestUserMail";
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(2L, "John", "Doe","987654321","+573101234455",
                                     LocalDate.of(2000, 1, 1),"owner@mail.com", "password",
                                     new Role(2L, "ROLE_PROPIETARIO", "Propietario")));
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        
        assertThrows(RestaurantOwnershipInvalidException.class, () -> restaurantUseCase.validateRestaurantOwnershipInternal(authHeader, 1L));
    }
}
