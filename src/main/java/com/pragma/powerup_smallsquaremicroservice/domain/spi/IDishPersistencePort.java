package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import org.springframework.data.domain.Page;

public interface IDishPersistencePort {
    void createDish(Dish dish);
    Dish getDishById(Long idDish);
    void updateDish(Dish dish);
    Page<Dish> getActiveDishesFromRestaurantPageable(Long idRestaurant, int page, int size);
    Page<Dish> getActiveDishesFromRestaurantPageableByCategory(Long idRestaurant, Long idCategory, int page, int size);
    boolean validateName(Dish dish);
    boolean validateCategoryExists(Long idCategory);
}
