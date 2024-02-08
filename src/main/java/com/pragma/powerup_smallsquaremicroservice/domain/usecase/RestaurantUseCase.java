package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    
    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }
    
    
    @Override
    public void createRestaurant(Restaurant restaurant) {
        if (validateName(restaurant.getName()) && validateNIT(restaurant.getNit()) && validateAddress(
                restaurant.getAddress()) && validatePhone(restaurant.getPhone()) && validateUrlLogo(
                restaurant.getUrlLogo()) && validateIdOwner(restaurant.getIdOwner())) {
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
}
