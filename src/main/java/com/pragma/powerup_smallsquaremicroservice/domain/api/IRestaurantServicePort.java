package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantServicePort {
    void createRestaurant(String authHeader, Restaurant restaurant);
    Page<Restaurant> getAllRestaurantsPageable(int page, int size);
    Page<Dish> getAllDishesFromRestaurantPageable(Long idRestaurant, Long idCategory, int page, int size);
    
    boolean validateName(String name);
    boolean validateNIT(String nit);
    boolean validateAddress(String address);
    boolean validatePhone(String phone);
    boolean validateUrlLogo(String urlLogo);
    boolean validateIdOwner(Long idOwner);
    boolean validateOwnerRoleFromRequest(String authHeader, Long idOwner);
    boolean validateRequestAdminRole(String authHeader);
    boolean validateRestaurantOwnership(String authHeader, Long idRestaurant);
    boolean validateRestaurantOwnershipInternal(String authHeader, Long idRestaurant);
}
