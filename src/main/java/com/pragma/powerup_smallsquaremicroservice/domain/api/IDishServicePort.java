package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;

public interface IDishServicePort {
    void createDish(Dish dish);
    Dish getDish(Long idDish);
    void updateDish(Long idDish, int dishPrice, String dishDescription);
    boolean validateName(Dish dish);
    boolean validateCategory(Long idCategory);
    boolean validateDescription(String description);
    boolean validatePrice(int price);
    boolean validateRestaurant(Long idRestaurant);
    boolean validateUrlImage(String urlImage);
}
