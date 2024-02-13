package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import feign.FeignException;

import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {
    private static final Long OWNER_ROLE_ID = 2L;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserMSClientPort userMSClientPort;
    
    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserMSClientPort userMSClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userMSClientPort = userMSClientPort;
    }
    
    
    @Override
    public void createRestaurant(Restaurant restaurant) {
        if (validateName(restaurant.getName()) && validateNIT(restaurant.getNit()) && validateAddress(
                restaurant.getAddress()) && validatePhone(restaurant.getPhone()) && validateUrlLogo(
                restaurant.getUrlLogo()) && validateOwnerRole(restaurant.getIdOwner())) {
            restaurantPersistencePort.createRestaurant(restaurant);
        }
    }
    
    
    @Override
    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^(?=.*[^\\d\\s])[\\w\\s]+$");
        if (!pattern.matcher(name).matches() || name.isEmpty()) {
            throw new NameInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateNIT(String nit) {
        Pattern pattern = Pattern.compile("^\\d{5,20}$");
        if (!pattern.matcher(nit).matches() || nit.isEmpty()) {
            throw new NITInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateAddress(String address) {
        if (address.isEmpty()) {
            throw new AddressInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile("^\\+?\\d{9,13}$");
        if (!pattern.matcher(phone).matches() || phone.isEmpty()) {
            throw new PhoneInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateUrlLogo(String urlLogo) {
        if (urlLogo.isEmpty()) {
            throw new UrlLogoInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateIdOwner(Long idOwner) {
        if (idOwner == null || idOwner <= 0) {
            throw new IdOwnerInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateOwnerRole(Long idOwner) {
        if (validateIdOwner(idOwner)) {
            try {
                if (!OWNER_ROLE_ID.equals(userMSClientPort.getOwnerById(idOwner).getRole().getId())){
                    throw new RoleNotAllowedException();
                }
            } catch (FeignException.FeignClientException e){
                throw new OwnerNotFoundException();
            }
        }
        return true;
    }
}
