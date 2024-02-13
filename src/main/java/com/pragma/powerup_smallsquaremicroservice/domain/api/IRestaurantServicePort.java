package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void createRestaurant(Restaurant restaurant);
    
    boolean validateName(String name);
    boolean validateNIT(String nit);
    boolean validateAddress(String address);
    boolean validatePhone(String phone);
    boolean validateUrlLogo(String urlLogo);
    boolean validateIdOwner(Long idOwner);
    boolean validateOwnerRole(Long idOwner);
    
}
