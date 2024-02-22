package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import feign.FeignException;

import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {
    private static final Long ADMIN_ROLE_ID = 1L;
    private static final Long OWNER_ROLE_ID = 2L;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserMSClientPort userMSClientPort;
    private final IJwtServicePort jwtServicePort;
    
    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort,
                             IUserMSClientPort userMSClientPort,
                             IJwtServicePort jwtServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userMSClientPort = userMSClientPort;
        this.jwtServicePort = jwtServicePort;
    }
    
    
    @Override
    public void createRestaurant(String authHeader, Restaurant restaurant) {
        if (validateRequestAdminRole(authHeader) && validateName(restaurant.getName())
                && validateNIT(restaurant.getNit()) && validateAddress(restaurant.getAddress())
                && validatePhone(restaurant.getPhone()) && validateUrlLogo(restaurant.getUrlLogo())
                && validateOwnerRoleFromRequest(authHeader, restaurant.getIdOwner())) {
            restaurantPersistencePort.createRestaurant(restaurant);
        }
    }
    
    
    @Override
    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^(?=.*[^\\d\\s])[\\w\\s]+$");
        if (!pattern.matcher(name).matches() || name.isEmpty()) {
            throw new RestaurantNameInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateNIT(String nit) {
        Pattern pattern = Pattern.compile("^\\d{5,20}$");
        if (!pattern.matcher(nit).matches() || nit.isEmpty()) {
            throw new RestaurantNitInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateAddress(String address) {
        if (address.isEmpty()) {
            throw new RestaurantAddressInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile("^\\+?\\d{9,13}$");
        if (!pattern.matcher(phone).matches() || phone.isEmpty()) {
            throw new RestaurantPhoneInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateUrlLogo(String urlLogo) {
        if (urlLogo.isEmpty()) {
            throw new RestaurantUrlLogoInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateIdOwner(Long idOwner) {
        if (idOwner == null || idOwner <= 0) {
            throw new RestaurantIdOwnerInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateOwnerRoleFromRequest(String authHeader, Long idOwner) {
        if (validateIdOwner(idOwner)) {
            try {
                if (!OWNER_ROLE_ID.equals(userMSClientPort.getOwnerById(authHeader, idOwner).getRole().getId())){
                    throw new RoleNotAllowedException();
                }
            } catch (FeignException.FeignClientException e){
                throw new OwnerNotFoundException();
            }
        }
        return true;
    }
    
    @Override
    public boolean validateRequestAdminRole(String authHeader) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        if (!ADMIN_ROLE_ID.equals(userMSClientPort.getUserByMail(authHeader, requestUserMail).getRole().getId())) {
            throw new UnauthorizedRoleException();
        }
        return true;
    }
}
