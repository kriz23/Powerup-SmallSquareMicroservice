package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import org.springframework.data.domain.Page;

public interface IRestaurantPersistencePort {
    void createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long idRestaurant);
    boolean validateRestaurantExists(Long idRestaurant);
    Page<Restaurant> getAllRestaurantsPageable(int page, int size);
}
